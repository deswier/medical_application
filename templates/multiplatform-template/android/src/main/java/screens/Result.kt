import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapplication.model.Note
import com.myapplication.model.TestNotes
import com.myapplication.tools.DateParser
import screens.profile.iconButton
import theme.BluePastel
import theme.DarkBlue
import theme.LightBeige
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

    Column(
        modifier = Modifier.fillMaxWidth().padding(0.dp, 150.dp).verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = DarkBlue,
                    backgroundColor = LightBeige,
                    focusedIndicatorColor = DarkBlue, //hide the indicator
                    unfocusedIndicatorColor = BluePastel
                ),
                value = "Date",
                readOnly = true,
                modifier = Modifier.width(130.dp),
                onValueChange = {
                })

            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = DarkBlue,
                    backgroundColor = LightBeige,
                    focusedIndicatorColor = DarkBlue, //hide the indicator
                    unfocusedIndicatorColor = BluePastel
                ),
                value = "Result",
                //  singleLine = true,
                readOnly = true,
                modifier = Modifier.width(130.dp),
                onValueChange = {
                })

            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = DarkBlue,
                    backgroundColor = LightBeige,
                    focusedIndicatorColor = DarkBlue, //hide the indicator
                    unfocusedIndicatorColor = BluePastel
                ),
                value = "Reference",
                singleLine = true,
                readOnly = true,
                modifier = Modifier.width(130.dp),
                onValueChange = {
                })
        }

        for (item in note) {
            Row(
                modifier = Modifier.fillMaxWidth().clickable(onClick = {})
            ) {
                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = DarkBlue,
                        backgroundColor = LightBeige,
                        focusedIndicatorColor = DarkBlue, //hide the indicator
                        unfocusedIndicatorColor = BluePastel
                    ),
                    value = DateParser.getShortDate(item.date),
                    //singleLine = true,
                    readOnly = true,
                    modifier = Modifier.width(130.dp),
                    onValueChange = {
                    })

                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = DarkBlue,
                        backgroundColor = LightBeige,
                        focusedIndicatorColor = DarkBlue, //hide the indicator
                        unfocusedIndicatorColor = BluePastel,
                        textColor = getResultColor(item)

                    ),
                    value = item.result +" "+ item.unit,
                    //  singleLine = true,
                    readOnly = true,
                    modifier = Modifier.width(130.dp),
                    onValueChange = {
                    })

                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = DarkBlue,
                        backgroundColor = LightBeige,
                        focusedIndicatorColor = DarkBlue, //hide the indicator
                        unfocusedIndicatorColor = BluePastel
                    ),
                    value = item.referenceRange +" "+  item.unit,
                    singleLine = true,
                    readOnly = true,
                    modifier = Modifier.width(130.dp),
                    onValueChange = {
                    })
            }

        }
    }
}

fun getResultColor(item: Note): Color {
    return if (item.isNormalResult) Color.Black;
    else Color.Red;
}

@Composable
@Preview
fun resultScreenPreview() {
    resultScreen()
}