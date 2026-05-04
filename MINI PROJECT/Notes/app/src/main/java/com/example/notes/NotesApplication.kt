package com.example.notes

import android.app.Application
import androidx.room.Room
import com.example.notes.dataBase.database

class NotesApplication : Application() {
    
    companion object {
        lateinit var notesDatabase: database
            private set
    }

    override fun onCreate() {
        super.onCreate()
        notesDatabase = Room.databaseBuilder(
            applicationContext,
            database::class.java,
            database.DatabaseName
        ).build()
    }
}
