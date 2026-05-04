package com.example.notes.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1, exportSchema = true)
abstract class database : RoomDatabase() {

    companion object {
        val DatabaseName = "Notes"
    }

    abstract fun getDao(): notesDao
}