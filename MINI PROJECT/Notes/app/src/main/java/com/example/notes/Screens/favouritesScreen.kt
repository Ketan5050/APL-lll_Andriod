package com.example.notes.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notes.R
import com.example.notes.auxilary.viewModel
import com.example.notes.ui.theme.primeCo
import com.example.notes.ui.theme.secDarkCo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(navController: NavController, viewModel: viewModel) {
    val favNotes by viewModel.favData.observeAsState(initial = emptyList())
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    "Favourites",
                    style = TextStyle(
                        fontWeight = FontWeight.W600,
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.padding(start = 10.dp)


                )
            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primeCo,
                    titleContentColor = secDarkCo,
                ),

                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        tint = secDarkCo,
                        contentDescription = "back to main screen",
                        modifier = Modifier
                            .size(38.dp)
                            .padding(start = 10.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }

            )
        }
    ) { it ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Column(modifier = Modifier.fillMaxSize()) {
                LazyVerticalStaggeredGrid(
                    reverseLayout = false,
                    contentPadding = PaddingValues(top = 50.dp, bottom = 100.dp),
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 10.dp,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    userScrollEnabled = true,
                    content = {
                        items(favNotes.size) {
                            NoteCard(
                                title = favNotes[it].title,
                                content = favNotes[it].content,
                                id = favNotes[it].id,
                                fav = favNotes[it].fav,
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 10.dp)
                )
            }


        }
    }

}