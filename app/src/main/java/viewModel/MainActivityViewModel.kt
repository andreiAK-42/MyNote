package viewModel

import com.example.mynote.MyApp
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import database.NoteDao
import database.NoteEntity
import javax.inject.Inject

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    @Inject
    lateinit var noteDao: NoteDao

    lateinit var allNoteList: MutableLiveData<MutableList<NoteEntity>>
    init {
        (application as MyApp).getNoteComponent().inject(this)
        allNoteList = MutableLiveData()
        getAllRecords()
    }

    fun getAllRecords() {
        val list = noteDao.getAllRecordsFromDB()
        allNoteList.value = list!!
    }

    fun deleteRecord(noteEntity: NoteEntity) {
        allNoteList.value!!.remove(noteEntity)
        noteDao.deleteRecord(noteEntity)
    }

    fun updateRecord(noteEntity: NoteEntity) {
        noteDao.updateRecord(noteEntity)
        getAllRecords()
    }

    fun insertRecord(noteEntity: NoteEntity) {
        noteDao.insertRecord(noteEntity)
        getAllRecords()
    }
}