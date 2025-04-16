package com.example.mynote

import database.NoteEntity

interface OnNoteDeleteListener {
    fun onNoteDelete(note: NoteEntity)
}