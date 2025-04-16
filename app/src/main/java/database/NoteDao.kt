package database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_entity ORDER BY id DESC")
    fun getAllRecordsFromDB(): MutableList<NoteEntity>?


    @Insert
    fun insertRecord(noteEntity: NoteEntity)

    @Delete
    fun deleteRecord(noteEntity: NoteEntity)

    @Update
    fun updateRecord(noteEntity: NoteEntity)
}