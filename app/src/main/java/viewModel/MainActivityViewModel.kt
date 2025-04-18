package viewModel

import com.example.mynote.MyApp
import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import database.NoteDao
import database.NoteEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    @Inject
    lateinit var noteDao: NoteDao

    val allNoteList = MutableLiveData<MutableList<NoteEntity>>(mutableListOf())
    init {
        (application as MyApp).getNoteComponent().inject(this)
        getAllRecords()
    }

    private fun getAllRecords() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = noteDao.getAllRecordsFromDB() ?: mutableListOf()
            allNoteList.postValue(list)
        }
    }

    fun deleteRecord(noteEntity: NoteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.deleteRecord(noteEntity)

            val updatedList = noteDao.getAllRecordsFromDB() ?: mutableListOf()
            allNoteList.postValue(updatedList)
        }

    }

    fun updateRecord(noteEntity: NoteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.updateRecord(noteEntity)
            getAllRecords()
        }
    }

    fun insertRecord(noteEntity: NoteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.insertRecord(noteEntity)
            getAllRecords()
        }

    }
}