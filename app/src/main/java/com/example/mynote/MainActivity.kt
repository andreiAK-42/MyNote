package com.example.mynote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import database.NoteEntity
import viewModel.MainActivityViewModel


class MainActivity : AppCompatActivity(), ToDoAdapter.OnNoteDeleteListener {
    lateinit var viewModel: MainActivityViewModel
    lateinit var todoAdapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()

        val recyclerView = findViewById<RecyclerView>(R.id.rview_todo)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        todoAdapter = ToDoAdapter(viewModel.allNoteList.value ?: mutableListOf(), listener = this)
        recyclerView.adapter = todoAdapter

        findViewById<Chip>(R.id.chip_addNewNote).setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startForResult.launch(intent)
        }

        viewModel.allNoteList.observe(this) { newList ->
            todoAdapter.updateList(newList)
        }

    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                viewModel.insertRecord(
                    NoteEntity(
                        title = data?.getStringExtra("title").toString(),
                        description = data?.getStringExtra("description").toString(),
                        state = 0
                    )
                )
            }
        }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getAllRecords()
    }

    override fun onNoteDelete(note: NoteEntity) {
        viewModel.deleteRecord(note)
    }

    override fun onNoteUpdate(note: NoteEntity) {
        viewModel.updateRecord(note)
    }
}

