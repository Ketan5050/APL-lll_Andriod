package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.notes.Screens.AddNotesScreen
import com.example.notes.Screens.FavouritesScreen
import com.example.notes.Screens.mainScreen
import com.example.notes.auxilary.viewModel
import com.example.notes.ui.theme.NotesTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        setContent {
            NotesTheme {
                val navController = rememberNavController()
                val mainViewModel: viewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = screen.mainScreen.routes,
                    enterTransition = { fadeIn(animationSpec = tween(durationMillis = 0)) }
                ) {
                    composable(route = screen.mainScreen.routes) {
                        mainScreen(navController, viewModel = mainViewModel)
                    }

                    composable<screen.addNotes> {
                        val noteData = it.toRoute<screen.addNotes>()
                        AddNotesScreen(
                            navController,
                            viewModel = mainViewModel,
                            noteData.title,
                            noteData.content,
                            noteData.id,
                            noteData.fav
                        )
                    }

                    composable(screen.favouritesScreen.routes) {
                        FavouritesScreen(navController, viewModel = mainViewModel)
                    }
                }
            }
        }
    }
}

@Serializable
sealed class screen(val routes: String) {
    @Serializable
    data object mainScreen : screen("mainScreen")

    @Serializable
    data class addNotes(val title: String, val content: String, val id: Int, val fav: Boolean) :
        screen("addNotes")

    @Serializable
    data object favouritesScreen : screen("favNotes")
}
