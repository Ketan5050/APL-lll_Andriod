package com.example.notes.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface notesDao {

    @Upsert
    fun upsert(notes: Notes)

    @Query("DELETE FROM Notes WHERE id=:id")
    fun delete(id: Int)

    @Query("SElECT * FROM Notes ORDER BY id DESC")
    fun getNotesByDate(): LiveData<List<Notes>>

    @Query("DELETE FROM Notes")
    fun deleteAllNotes()

    @Query("UPDATE Notes SET fav = 1 WHERE id = :id")
    fun makeFav(id: Int)

    @Query("SELECT * FROM Notes WHERE fav = 1 ORDER BY id DESC")
    fun getFavNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM notes WHERE title LIKE :query OR content LIKE :query")
    fun searchNotes(query: String): Flow<List<Notes>>


}