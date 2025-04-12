package di

import android.app.Application
import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Note
import dagger.Module
import dagger.Provides
import database.NoteDao
import database.NoteDatabase
import javax.inject.Singleton

@Module
class NoteModule(val application: Application) {

    @Singleton
    @Provides
    fun getNoteDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.getNoteDao()
    }

    @Singleton
    @Provides
    fun getRoomDbInstance(): NoteDatabase {
        return NoteDatabase.getNoteDatabaseInstance(provideAppContext())
    }

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }
}