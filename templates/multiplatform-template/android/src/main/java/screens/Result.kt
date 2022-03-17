import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.myapplication.model.TestNotes
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
@Composable
fun ResultScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
        //   .background(Color.Blue),
        //contentAlignment = Alignment.
    ) {
        val note = remember { TestNotes() }
        var search by rememberSaveable { mutableStateOf("") }
        var searchRes by rememberSaveable { mutableStateOf(note.searchNote(search)) }

        iconButton(10.dp, 90.dp, 24.dp)
        fieldRes(10.dp, 120.dp, searchRes.toString())

        TextField(
            value = search,
            placeholder = { Text("Find result") },
            singleLine = true,
            modifier = Modifier.width(200.dp).padding(10.dp),
            readOnly = false,
            onValueChange = {
                search = it
                System.out.println(search)
                searchRes = if (search == "") {
                    note.notes
                } else note.searchNote(search)
            }
        )

//        Text(
//            text = "RESULT",
//            fontSize = MaterialTheme.typography.h3.fontSize,
//            fontWeight = FontWeight.Bold,
//            color = Color.White
//        )
//

    }
}

@Composable
fun iconButton(x: Dp, y: Dp, size: Dp) {
    val navController = rememberNavController()
    IconButton(modifier = Modifier.padding(x, y).size(size),
        onClick = {
         //   newResultScreen()
        }) {
        Icon(

            Icons.Filled.Add,
            "contentDescription",
            tint = Color.Green
        )
    }
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

@Composable
@Preview
fun ResultScreenPreview() {
    ResultScreen()
}