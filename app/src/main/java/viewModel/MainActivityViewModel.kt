package viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import database.NoteDao
import database.NoteEntity
import javax.inject.Inject

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    @Inject
    lateinit var noteDao: NoteDao

    lateinit var allNoteList: MutableLiveData<List<NoteEntity>>
    init {
        allNoteList = MutableLiveData()

    }

    fun getRecordsObserver(): MutableLiveData<List<NoteEntity>> {
        return allNoteList
    }

    fun getAllRecords() {
        val list = noteDao.getAllRecordsFromDB()
        allNoteList.postValue(list)
    }

    fun insertRecord(noteEntity: NoteEntity) {
        noteDao.insertRecord(noteEntity)
    }
}