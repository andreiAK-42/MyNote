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

    fun getRecordsObserver(): MutableLiveData<MutableList<NoteEntity>> {
        return allNoteList
    }

    fun getAllRecords() {
        val list = noteDao.getAllRecordsFromDB()
        allNoteList.value = list!!
    }

    fun deleteRecord(noteEntity: NoteEntity) {
        noteDao.deleteRecord(noteEntity)
    }

    fun updateRecord(noteEntity: NoteEntity) {
        noteDao.updateRecord(noteEntity)
    }

    fun insertRecord(noteEntity: NoteEntity) {
        noteDao.insertRecord(noteEntity)
        getAllRecords()
    }
}