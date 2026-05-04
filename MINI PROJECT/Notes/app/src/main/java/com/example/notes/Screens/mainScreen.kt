package com.example.notes.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.platform.LocalLayoutDirection
import com.example.notes.R
import com.example.notes.auxilary.viewModel
import com.example.notes.screen
import com.example.notes.ui.theme.cardColor1
import com.example.notes.ui.theme.cardColor2
import com.example.notes.ui.theme.cardColor3
import com.example.notes.ui.theme.cardColor4
import com.example.notes.ui.theme.cardColor5
import com.example.notes.ui.theme.darkCo
import com.example.notes.ui.theme.primeCo
import com.example.notes.ui.theme.secDarkCo
import com.example.notes.ui.theme.star
import kotlinx.coroutines.launch

import kotlin.math.abs

val colorList = listOf(cardColor1, cardColor2, cardColor3, cardColor4, cardColor5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainScreen(navController: NavController, viewModel: viewModel) {
    var query by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val data by viewModel.data.observeAsState(emptyList())
    val searchedData by viewModel.filteredNotes.collectAsState(emptyList())
    var expandedSearchBar by rememberSaveable { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = !expandedSearchBar,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .shadow(16.dp)
                    .width(300.dp)
            ) {
                Text(
                    text = "Notes",
                    textAlign = TextAlign.Start,
                    style = TextStyle(fontSize = 24.sp),
                    modifier = Modifier
                        .height(100.dp)
                        .padding(20.dp)
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Favourites", style = TextStyle(fontSize = 18.sp)) },
                    selected = false,
                    onClick = {
                        navController.navigate(screen.favouritesScreen.routes)
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.fav_symbol),
                            contentDescription = "favList",
                            tint = star
                        )
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Delete All Notes", style = TextStyle(fontSize = 18.sp)) },
                    selected = false,
                    onClick = {
                        viewModel.deleteAllNotes()
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = star
                        )
                    }
                )
            }
        }
    ) {
        Scaffold(
            containerColor = primeCo,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = primeCo,
                        titleContentColor = secDarkCo
                    ),
                    title = {
                        Text(
                            "Notes..",
                            style = TextStyle(fontWeight = FontWeight.W600, fontSize = 24.sp)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                tint = secDarkCo,
                                contentDescription = "Menu",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(
                            screen.addNotes(title = "", content = "", id = -1, fav = false)
                        )
                    },
                    shape = CircleShape,
                    containerColor = secDarkCo,
                    modifier = Modifier.padding(bottom = 25.dp, end = 5.dp)
                ) {
                    Icon(Icons.Filled.Add, "Add Note", tint = Color.White)
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(
                    top = innerPadding.calculateTopPadding() - 15.dp,
                    bottom = innerPadding.calculateBottomPadding(),
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
                )
            ) {
                SearchBar(
                    colors = SearchBarDefaults.colors(MaterialTheme.colorScheme.onPrimary),
                    shadowElevation = 15.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (!expandedSearchBar) 10.dp else 0.dp),
                    placeholder = { Text(text = "search") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "search icon") },
                    trailingIcon = {
                        if (expandedSearchBar) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "close icon",
                                modifier = Modifier.clickable {
                                    if (query.isEmpty()) expandedSearchBar = false else query = ""
                                }
                            )
                        }
                    },
                    query = query,
                    onQueryChange = {
                        query = it
                        viewModel.searchedQuery.value = it
                    },
                    onSearch = {},
                    active = expandedSearchBar,
                    onActiveChange = { expandedSearchBar = it }
                ) {
                    LazyColumn(Modifier.fillMaxSize()) {
                        items(
                            count = searchedData.size,
                            key = { index -> searchedData[index].id }
                        ) { index ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RectangleShape,
                                border = BorderStroke(width = 1.dp, color = Color.LightGray),
                                colors = CardDefaults.cardColors(Color.White)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .clickable {
                                            navController.navigate(
                                                screen.addNotes(
                                                    title = searchedData[index].title,
                                                    content = searchedData[index].content,
                                                    id = searchedData[index].id,
                                                    fav = searchedData[index].fav
                                                )
                                            )
                                            query = ""
                                            expandedSearchBar = false
                                        }
                                ) {
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Icon(Icons.Default.KeyboardArrowUp, "search results")
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(
                                        text = searchedData[index].title,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    }
                }

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    contentPadding = PaddingValues(10.dp),
                    verticalItemSpacing = 10.dp,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        count = data.size,
                        key = { index -> data[index].id }
                    ) { index ->
                        NoteCard(
                            title = data[index].title,
                            content = data[index].content,
                            id = data[index].id,
                            fav = data[index].fav,
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    title: String,
    content: String,
    id: Int,
    fav: Boolean,
    viewModel: viewModel,
    navController: NavController
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .wrapContentHeight()
            .combinedClickable(
                onLongClick = { viewModel.deleteNote(id) },
                onClick = {
                    navController.navigate(
                        screen.addNotes(title = title, content = content, id = id, fav = fav)
                    )
                }
            )
            .shadow(15.dp, spotColor = darkCo, ambientColor = darkCo, clip = true, shape = RoundedCornerShape(15.dp)),
        colors = CardDefaults.cardColors(colorList[abs(id % 5)]),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title.uppercase(),
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontSize = 16.sp)
            )
            if (content.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                Text(
                    text = content,
                    style = TextStyle(fontSize = 12.sp),
                    maxLines = 6,
                    modifier = Modifier.height(if (id % 2 == 0) 70.dp else 100.dp)
                )
            }
        }
    }
}
