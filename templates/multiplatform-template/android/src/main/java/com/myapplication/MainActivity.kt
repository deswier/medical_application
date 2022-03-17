package com.myapplication
import MainScreen
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.myapplication.model.TestNotes
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import theme.BottomNavBarDemoTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {

//            val note = remember { TestNotes() }
//            var search by rememberSaveable { mutableStateOf("") }
//            var searchRes by rememberSaveable { mutableStateOf(note.searchNote(search)) }
            BottomNavBarDemoTheme {
                MainScreen()
            }
        //            iconButton(10.dp,90.dp,24.dp)
//            fieldRes(10.dp, 120.dp, searchRes.toString())
//
//            TextField(
//                value = search,
//                placeholder = { Text("Find result") },
//                singleLine = true,
//                modifier = Modifier.width(200.dp).padding(10.dp),
//                readOnly = false,
//                onValueChange = {
//                    search = it
//                    searchRes = note.searchNote(search)
//                }
//            )
        }
    }

}

@Composable
fun iconButton(x: Dp, y: Dp, size: Dp) {
    IconButton(modifier = Modifier.padding(x, y).size(size),
        onClick = { addNewResultWindow() }) {
        Icon(

            Icons.Filled.Add,
            "contentDescription",
            tint = Color.Green
        )
    }
}

fun addNewResultWindow() {
    TODO("Not yet implemented")
}


@Composable
private fun fieldRes(x: Dp, y: Dp, searchRes: String) {
    TextField(
        value = searchRes,
        readOnly = true,
        modifier = Modifier.padding(x, y),
        onValueChange = {
        }
    )
}