import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun App() {
    MaterialTheme {
        var text by remember { mutableStateOf("Hello, World!") }

        Button(onClick = {
            text = "Hello, ${getPlatformName()}"
        }) {
            Text(text)
        }

    }


//         val note = remember { TestNotes() }
//         var find by rememberSaveable { mutableStateOf("") }
//         var findRes by rememberSaveable { mutableStateOf(note.findNote(find)) }
//
//         TextField(
//             value = "find",
//             singleLine = true,
//             onValueChange = {
//                 find = it
//                 findRes=note.findNote(find)
//             }
//         )
//
//         TextField(
//             value = findRes.toString(),
//             readOnly = true,
//             onValueChange = {
//
//             }
//         )
   // }

}

expect fun getPlatformName(): String