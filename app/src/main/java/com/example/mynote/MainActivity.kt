package com.example.mynote

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.ui.ChipManager
import com.example.mynote.ui.ToDoAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import database.NoteEntity
import viewModel.MainActivityViewModel


class MainActivity : AppCompatActivity(), ToDoAdapter.OnNoteDeleteListener {
    lateinit var viewModel: MainActivityViewModel
    lateinit var todoAdapter: ToDoAdapter
    lateinit var chipManager: ChipManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        initAdapters()


        findViewById<Chip>(R.id.chip_addNewNote).setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startForResult.launch(intent)
        }

        viewModel.allNoteList.observe(this) { newList ->
            todoAdapter.updateList(newList)
        }

        val chipGroup = findViewById<ChipGroup>(R.id.chipGroup)
        chipManager = ChipManager(chipGroup, this)

        chipManager.addChip("All (${viewModel.allNoteList.value!!.count()})")
        chipManager.addChip("Important (0)", Color.parseColor("#8BD2C9"))
        chipManager.addChip(
            "To-Do (${viewModel.allNoteList.value!!.count()})",
            Color.parseColor("#F4D798")
        )
        chipManager.addChip("Reminder (0)", Color.parseColor("#F6A5A0"))

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getAllRecords()
    }

    override fun onNoteDelete(note: NoteEntity) {
        viewModel.deleteRecord(note)
        chipManager.updateChip(0, "All (${viewModel.allNoteList.value!!.count()})")
        chipManager.updateChip(2, "To-Do (${viewModel.allNoteList.value!!.count()})")
    }

    override fun onNoteUpdate(note: NoteEntity) {
        viewModel.updateRecord(note)
    }

    fun initAdapters() {
        val recyclerView = findViewById<RecyclerView>(R.id.rview_todo)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        todoAdapter = ToDoAdapter(viewModel.allNoteList.value ?: mutableListOf(), listener = this)
        recyclerView.adapter = todoAdapter
    }
    override fun onViewNote(note: NoteEntity) {
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra("title", note.title)
        intent.putExtra("state", note.state)
        intent.putExtra("id", note.id)
        intent.putExtra("description", note.description)
        startForResult.launch(intent)
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val _id = data?.getIntExtra("id", -1) ?: -1
                if (_id != -1) {
                    viewModel.updateRecord(
                        (NoteEntity(
                            id = _id,
                            title = data?.getStringExtra("title").toString(),
                            description = data?.getStringExtra("description").toString(),
                            state = data?.getIntExtra("state", 0) ?: 0
                        ))
                    )
                }
                else {
                    viewModel.insertRecord(
                        NoteEntity(
                            title = data?.getStringExtra("title").toString(),
                            description = data?.getStringExtra("description").toString(),
                            state = 0
                        )
                    )
                }

            }
        }

}

