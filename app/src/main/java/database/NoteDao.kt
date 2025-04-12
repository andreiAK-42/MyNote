package database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Query("SELECT  * FROM note_entity ORDER BY id DESC")
    fun getAllRecordsFromDB(): List<NoteEntity>?


    @Insert
    fun insertRecord(noteEntity: NoteEntity)
}