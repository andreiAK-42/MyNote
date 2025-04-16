package database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    companion object {
        private var db_instance: NoteDatabase? = null

        fun getNoteDatabaseInstance(context: Context): NoteDatabase {
            if (db_instance == null) {
                db_instance = Room.databaseBuilder<NoteDatabase>(
                    context.applicationContext, NoteDatabase::class.java, "note_db"
                )
                    .allowMainThreadQueries()
                    .build()

            }
            return db_instance!!
        }
    }
}