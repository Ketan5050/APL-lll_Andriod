package com.example.notes.Screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notes.R
import com.example.notes.auxilary.viewModel
import com.example.notes.ui.theme.primeCo
import com.example.notes.ui.theme.secDarkCo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun check(id: Int): Boolean {

    if (id == -1) {
        return true
    } else {
        return false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotesScreen(
    navController: NavHostController, viewModel: viewModel,
    getTitle: String, getContent: String, getId: Int, getFav: Boolean
) {


    val context = LocalContext.current
    val formattedDateTime = remember {
        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy | hh:mm a")
        LocalDateTime.now().format(formatter)
    }


    var fav by remember {
        mutableStateOf(getFav)
    }

    var title by remember {
        mutableStateOf(getTitle)
    }

    var content by remember {
        mutableStateOf(value = getContent)
    }


    Scaffold(
        containerColor = primeCo,
        topBar = {

            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = primeCo,
                titleContentColor = secDarkCo,
            ),
                title = {
                    Text(
                        "",
                        style = TextStyle(
                            fontWeight = FontWeight.W600,
                            fontSize = 24.sp
                        ),
                        modifier = Modifier.padding(start = 10.dp)


                    )
                },
                modifier = Modifier.padding(end = 20.dp, top = 10.dp),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            tint = secDarkCo,
                            contentDescription = "Localized description",
                            modifier = Modifier.size(32.dp)


                        )
                    }
                },
                actions = {
                    IconButton(onClick = {

                        fav = !fav
                    }, modifier = Modifier.size(28.dp)) {
                        Icon(
                            painter = painterResource(
                                id = if (fav) {
                                    R.drawable.fav_filled
                                } else {
                                    R.drawable.fav_outlined
                                }
                            ),
                            tint = if (fav) Color.Red else secDarkCo,
                            contentDescription = "favourite",
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = "save",
                        tint = secDarkCo,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                if (title.isEmpty()) {
                                    Toast
                                        .makeText(context, "Title is empty", Toast.LENGTH_SHORT)
                                        .show()
                                } else {

                                    if (check(getId)) {
                                        viewModel.saveData(
                                            title = title.trimEnd(),
                                            content = content.trimEnd(),
                                            fav = fav
                                        )
                                    } else {
                                        viewModel.updateData(
                                            id = getId,
                                            title = title.trimEnd(),
                                            content = content.trimEnd(),
                                            fav = fav
                                        )
                                    }

                                    navController.popBackStack()
                                }

                            }

                    )


                }
            )


        }

    ) { innerpadding ->
        Box(modifier = Modifier.padding(innerpadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                TextField(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(start = 20.dp)
                        .shadow(
                            elevation = 0.dp,
                            spotColor = Color.Red,
                            ambientColor = Color.Red,
                            clip = true,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    ),
                    maxLines = 2,
                    value = title, onValueChange = {
                        title = it
                    },
                    placeholder = {
                        Text(
                            text = "Title..",
                            color = Color.Gray,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W600
                        )
                    },
                    shape = RoundedCornerShape(30),
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W600
                    )


                )

                Text(
                    text = formattedDateTime,
                    modifier = Modifier.padding(start = 50.dp, bottom = 10.dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                )

                TextField(
                    value = content, onValueChange = { it ->
                        content = it
                    },
                    placeholder = {
                        Text(text = "Notes...", color = Color.Gray, fontSize = 18.sp)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 0.dp, bottom = 30.dp, start = 20.dp, end = 20.dp)
                        .shadow(
                            elevation = 0.dp, spotColor = Color.Cyan,
                            ambientColor = Color.Black, clip = true,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(5)

                )

            }
        }

    }


}