package com.example.mynote

import NoteAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.SearchView
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

    private lateinit var searchView: SearchView
    private lateinit var listView: ListView
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        initViewModel()
        initAdapters()

        findViewById<Chip>(R.id.chip_addNewNote).setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startForResult.launch(intent)
        }

        val chipGroup = findViewById<ChipGroup>(R.id.chipGroup)
        chipManager = ChipManager(chipGroup, this, viewModel)

        setupSearch()
    }

    private fun setupViews() {
        searchView = findViewById(R.id.searchView_searchNotes)
        listView = findViewById(R.id.listview)
    }

    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterNotes(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    findViewById<LinearLayout>(R.id.linearLayout_main).visibility = View.GONE
                    findViewById<ListView>(R.id.listview).visibility = View.VISIBLE
                    filterNotes(newText)
                } else {
                    findViewById<LinearLayout>(R.id.linearLayout_main).visibility = View.VISIBLE
                    findViewById<ListView>(R.id.listview).visibility = View.GONE
                }
                return true
            }
        })
    }

    private fun filterNotes(query: String) {
        viewModel.allNoteList.value?.let { notes ->
            val filtered = if (query.isEmpty()) {
                notes
            } else {
                notes.filter { note ->
                    note.title.contains(query, ignoreCase = true) ||
                            note.description.contains(query, ignoreCase = true)
                }
            }
            adapter.updateData(filtered)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.allNoteList.observe(this) { notes ->
            chipManager.updateChip(0, "All (${viewModel.allNoteList.value!!.count()})")
            chipManager.updateChip(2, "To-Do (${viewModel.allNoteList.value!!.count()})")
            adapter.updateData(notes)
            todoAdapter.updateList(notes)
        }
    }

    override fun onNoteDelete(note: NoteEntity) {
        viewModel.deleteRecord(note)
    }

    override fun onNoteUpdate(note: NoteEntity) {
        viewModel.updateRecord(note)
    }

    private fun initAdapters() {
        val recyclerView = findViewById<RecyclerView>(R.id.rview_todo)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        todoAdapter = ToDoAdapter(viewModel.allNoteList.value ?: mutableListOf(), listener = this)
        recyclerView.adapter = todoAdapter

        adapter = NoteAdapter(this, viewModel.allNoteList.value!!)
        listView.adapter = adapter
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
                } else {
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

