package com.example.mynote

import android.app.Application
import di.NoteComponent
import di.DaggerNoteComponent
import di.NoteModule

class MyApp: Application() {

    private lateinit var noteComponent: NoteComponent

    override fun onCreate() {
        super.onCreate()

        noteComponent = DaggerNoteComponent.builder()
            .noteModule(NoteModule(this))
            .build()
    }

    fun getNoteComponent(): NoteComponent {
        return noteComponent
    }
}