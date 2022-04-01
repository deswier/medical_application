import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.model.FullName
import com.myapplication.model.Note
import com.myapplication.model.TestNotes
import com.myapplication.tools.DateParser
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun resultScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val note = remember { TestNotes() }

        // ModalDrawerSample()
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            //   BottomDrawerSample()

            var search by rememberSaveable { mutableStateOf("") }
            var searchRes by rememberSaveable { mutableStateOf(note.searchNote(search)) }

            Scaffold(
                //  modifier = Modifier.padding(0.dp, 150.dp),
                topBar = {
                    TopAppBar {
                        Text("Healthynetic", fontSize = 22.sp)
                        Spacer(Modifier.weight(1f, true))
                        ProvideTextStyle(
                            TextStyle(color = Color.White, fontSize = 8.sp)
                        ) {}
                        IconButton(
                            onClick = {
                                // actionAdder.value=true
                            }) {
                            Icon(
                                Icons.Filled.Add,
                                "contentDescription",
                            )
                        }
                        //   ModalDrawerSample()
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
                        //  Icon(Icons.Filled.Search, contentDescription = "Поиск")
                    }
                    fieldRes(searchRes)
                }


            }
        }
        //hhhhere  ModalDrawerSample()
      //  val update =
            BottomDrawerSample()
      //  if (update != null) note.add(update)
    }
}


@Composable
private fun fieldRes(note: ArrayList<Note>) {
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
//        ModalDrawerSample()
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        ) {
            for (item in note) {
                Row(
                    modifier = Modifier.fillMaxWidth().clickable(onClick = {}).padding(fieldEmptyWidth, 0.dp)
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

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun BottomDrawerSample() {
    var note: Note? = null
    val saveAction = remember { mutableStateOf(false) }

    val (gesturesEnabled, toggleGesturesEnabled) = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    Column {
        val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
        BottomDrawer(
            gesturesEnabled = gesturesEnabled,
            drawerState = drawerState,
            drawerContent = {
                var fName by rememberSaveable { mutableStateOf("") }

                TextField(
                    value = fName,
                    placeholder = { Text("name") },
                    singleLine = true,
                    modifier = Modifier.width(200.dp).padding(10.dp),
                    readOnly = false,
                    onValueChange = {
                        fName = it
                    }
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp),
                ) {
                    Button(
                        onClick = { scope.launch { drawerState.close() } },
                        content = { Text("Close Drawer") }
                    )
                    Button(
                        onClick = {
                            scope.launch { drawerState.close() }
                            note = Note(
                                "Invitro", FullName(fName, "Test"),
                                fName, DateParser.parseStringToDate("2020-01-01"), "7", "не выявленно", "мкмоль/л", null
                            )
                            // return note;
                        },
                        content = { Text("Save Drawer") }
                    )
                }
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp),
                    onClick = { scope.launch { drawerState.close() } },
                    content = { Text("Close Drawer") }
                )
                LazyColumn {
                    items(25) {
                        ListItem(
                            text = { Text("Item $it") },
                            icon = { Icon(Icons.Default.Favorite, contentDescription = "Localized description") }
                        )
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier.fillMaxSize().padding(365.dp, 3.dp).background(Color.Black),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Spacer(Modifier.height(20.dp))
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(
                            Icons.Filled.Add,
                            "AddResult",
                            tint = Color.Black
                        )
                    }
                }
            }
        )
    }
    // return note;
}