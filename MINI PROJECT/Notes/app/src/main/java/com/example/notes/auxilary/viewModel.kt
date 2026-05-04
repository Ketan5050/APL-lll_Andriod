package com.example.notes.auxilary

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.notes.NotesApplication
import com.example.notes.dataBase.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.time.LocalDate

class viewModel() : ViewModel() {
    private val dao = NotesApplication.notesDatabase.getDao()

    val data: LiveData<List<Notes>> = dao.getNotesByDate()
    val favData: LiveData<List<Notes>> = dao.getFavNotes()
    val searchedQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val filteredNotes = searchedQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(emptyList())
            } else {
                dao.searchNotes("%$query%")
            }
        }

    fun saveData(title: String, content: String, fav: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.upsert(
                Notes(
                    title = title,
                    content = content,
                    date = LocalDate.now().toString(),
                    fav = fav
                )
            )
        }
    }

    fun updateData(title: String, content: String, fav: Boolean = false, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.upsert(
                Notes(
                    title = title,
                    content = content,
                    id = id,
                    date = LocalDate.now().toString(),
                    fav = fav
                )
            )
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(id)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteAllNotes()
        }
    }
}
