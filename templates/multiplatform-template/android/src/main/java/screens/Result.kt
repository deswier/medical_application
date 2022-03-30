import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.model.Note
import com.myapplication.model.TestNotes
import com.myapplication.tools.DateParser
import screens.profile.iconButton
import theme.*
import java.util.ArrayList

@Composable
fun resultScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val note = remember { TestNotes() }
        var search by rememberSaveable { mutableStateOf("") }
        var searchRes by rememberSaveable { mutableStateOf(note.searchNote(search)) }

        iconButton(10.dp, 90.dp, 24.dp)
        fieldRes(searchRes)
        TextField(
            value = search,
            placeholder = { Text("Find result") },
            singleLine = true,
            modifier = Modifier.width(200.dp).padding(10.dp),
            readOnly = false,
            onValueChange = {
                search = it
                searchRes = if (search == "") {
                    note.notes
                } else note.searchNote(search)
            }
        )
    }
}

@Composable
private fun fieldRes(note: ArrayList<Note>) {
    val maxWidth = 390.dp
    val fieldDateWidth = (maxWidth.value / 7).dp
    val fieldTestWidth = (maxWidth.value / 3).dp
    val fieldResultWidth = (maxWidth.value / 4).dp
    val fieldEmptyWidth = (maxWidth.value / 50).dp
    val fieldReferenceWidth = maxWidth - fieldDateWidth - fieldResultWidth - fieldTestWidth - fieldEmptyWidth * 3
    val fontSize = 14.sp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(0.dp, 150.dp)
    ) {
        val backgroundColor = BeigePastel
        Row(
            modifier = Modifier.fillMaxWidth().background(backgroundColor)
        ) {
            Text(
                "\nDate\n", fontSize = fontSize, modifier = Modifier.width(fieldDateWidth)
            )
            emptyField(fieldEmptyWidth)

            Text(
                "\nTest\n", fontSize = fontSize, modifier = Modifier.width(fieldTestWidth)
            )
            emptyField(fieldEmptyWidth)

            Text(
                "\nResult\n", fontSize = fontSize, modifier = Modifier.width(fieldResultWidth)
            )
            emptyField(fieldEmptyWidth)

            Text(
                "\nReference\n", fontSize = fontSize, modifier = Modifier.width(fieldReferenceWidth)
            )
        }
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        ) {
            for (item in note) {
                Row(
                    modifier = Modifier.fillMaxWidth().clickable(onClick = {})
                ) {
                    Text(
                        "\n" + DateParser.getShortDate(item.date) + "\n",
                        fontSize = fontSize,
                        modifier = Modifier.width(fieldDateWidth)
                    )
                    emptyField(fieldEmptyWidth)
                    Text(
                        "\n" + item.test + "\n", fontSize = fontSize, modifier = Modifier.width(fieldTestWidth)
                    )
                    emptyField(fieldEmptyWidth)
                    Text(
                        "\n" + item.result + " " + item.unit + "\n",
                        fontSize = fontSize,
                        modifier = Modifier.width(fieldResultWidth),
                        color = getResultColor(item)
                    )
                    emptyField(fieldEmptyWidth)
                    Text(
                        "\n" + item.referenceRange + "\n" + " " + item.unit,
                        fontSize = fontSize,
                        modifier = Modifier.width(fieldReferenceWidth)
                    )
                }
            }
        }
    }
}

fun getResultColor(item: Note): Color {
    return if (item.isNormalResult) Color.Black
    else Color.Red
}

@Composable
fun emptyField(fieldEmptyWidth: Dp) {
    Text("", Modifier.width(fieldEmptyWidth))
}

@Composable
@Preview
fun resultScreenPreview() {
    resultScreen()
}