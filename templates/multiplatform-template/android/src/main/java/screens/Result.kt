import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myapplication.model.Note
import com.myapplication.storage.TestNotes
import com.myapplication.tools.DateParser
import screens.MainDestinations
import java.util.*

@Composable
fun resultScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val note = remember { TestNotes() }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            var search by rememberSaveable { mutableStateOf("") }
            var searchRes by rememberSaveable { mutableStateOf(note.searchNote(search)) }

            Scaffold(
                topBar = {
                    TopAppBar {
                        Text("Healthynetic", fontSize = 22.sp)//, modifier = Modifier.padding(50.dp))
                        Spacer(Modifier.weight(1f, true))
                        ProvideTextStyle(
                            TextStyle(color = Color.White, fontSize = 8.sp)
                        ) {}
                        IconButton(
                            onClick = {
                                navController.navigate("adderResult")
                            }) {
                            Icon(
                                Icons.Filled.Add,
                                "contentDescription",
                            )
                        }
                    }
                },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        val focusManager = LocalFocusManager.current
                        TextField(
                            value = search,
                            placeholder = {
                                Row() {
                                    Icon(Icons.Filled.Search, contentDescription = "Поиск")
                                    Text("Search")
                                }
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(15.dp)),
                            readOnly = false,
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            ),
                            onValueChange = {
                                search = it
                                searchRes = if (search == "") {
                                    note.notes
                                } else note.searchNote(search)
                            }
                        )
                    }
                    fieldRes(searchRes, navController)
                }
            }
        }
    }
}


@Composable
private fun fieldRes(note: ArrayList<Note>,navController: NavHostController) {
    val maxWidth = 390.dp
    val fieldDateWidth = (maxWidth.value / 7).dp
    val fieldTestWidth = (maxWidth.value / 3).dp
    val fieldResultWidth = (maxWidth.value / 4).dp
    val fieldEmptyWidth = (maxWidth.value / 50).dp
    val fieldReferenceWidth = maxWidth - fieldDateWidth - fieldResultWidth - fieldTestWidth - fieldEmptyWidth * 4
    val fontSize = 14.sp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(0.dp, 5.dp)
    ) {
        val backgroundColor = Color.White
        Row(
            modifier = Modifier.fillMaxWidth().background(backgroundColor)
                .border(width = 2.dp, color = Color.LightGray),
        ) {
            ProvideTextStyle(TextStyle(fontWeight = FontWeight.Medium, fontSize = fontSize)) {
                emptyField(fieldEmptyWidth)
                Text(
                    "\nDate\n", modifier = Modifier.width(fieldDateWidth)
                )
                emptyField(fieldEmptyWidth)

                Text(
                    "\nTest\n", modifier = Modifier.width(fieldTestWidth)
                )
                emptyField(fieldEmptyWidth)

                Text(
                    "\nResult\n", modifier = Modifier.width(fieldResultWidth)
                )
                emptyField(fieldEmptyWidth)

                Text(
                    "\nReference\n", modifier = Modifier.width(fieldReferenceWidth)
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        ) {
            for (item in note) {
                Row(
                    modifier = Modifier.fillMaxWidth().clickable(onClick = {
                        val uuid=item.uuid.toString()
                        navController.navigate("${MainDestinations.SHOW_RESULT}/$uuid")
                    }).padding(fieldEmptyWidth, 0.dp)
                ) {
                    Text(
                        "\n" + DateParser.convertToString(item.date) + "\n",
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