import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun newResultScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Scaffold(
            topBar = {
                TopAppBar {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        Modifier.width(50.dp)
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            "contentDescription",
                        )
                    }
                    Text("Healthynetic", fontSize = 22.sp)
                    Spacer(Modifier.weight(1f, true))

                }
            },
        ) {
            ProvideTextStyle(
                TextStyle(color = Color.Black)
            ) {
                var lab by rememberSaveable { mutableStateOf("") }
                var test by rememberSaveable { mutableStateOf("") }
                var result by rememberSaveable { mutableStateOf("") }
                var unit by rememberSaveable { mutableStateOf("") }
                var referenceRange by rememberSaveable { mutableStateOf("") }
                var comment by rememberSaveable { mutableStateOf("") }
                var date by rememberSaveable { mutableStateOf("") }

                val focusManager = LocalFocusManager.current
                val widthField = 350.dp
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(350.dp)
                        .padding(30.dp, 0.dp),
                ) {
                    Column {
                        date = fieldInput(date, "Date", widthField)
                        required()
                    }

                    Column {
                        test = fieldInput(test, "Test", widthField)
                        required()
                    }
                    Row() {
                        Column {
                            result = fieldInput(result, "Result", 200.dp)
                            required()
                        }
                        TextField(value = unit,
                            label = { Text("Unit") },
                            singleLine = true,
                            modifier = Modifier.width(widthField - 200.dp).clip(RoundedCornerShape(3.dp))
                                .padding(0.dp, 10.dp),
                            onValueChange = {
                                unit = it
                            })
                    }
                    Row() {
                        Column {
                            referenceRange = fieldInput(referenceRange, "Reference Range", 200.dp)
                            required()
                        }
                        TextField(value = unit,
                            enabled = false,
                            singleLine = true,
                            modifier = Modifier.width(widthField - 200.dp).clip(RoundedCornerShape(7.dp))
                                .padding(0.dp, 10.dp),
                            onValueChange = {
                            })
                    }
                    lab = fieldInput(lab, "Lab", widthField)
                    comment = fieldInput(comment, "Comment", widthField)
                    IconButton(
                        onClick = { },
                        Modifier.width(50.dp)
                    ) {
                        Icon(
                            Icons.Filled.Done,
                            "contentDescription",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun required() {
    Text("required", fontSize = 12.sp, modifier = Modifier.padding(10.dp, 0.dp), color = Color.DarkGray)
}

@Composable
fun fieldInput(
    text: String, label: String, widthField: Dp
): String {
    var v by rememberSaveable { mutableStateOf(text) }
    TextField(value = v,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.width(widthField).clip(RoundedCornerShape(3.dp)).padding(0.dp, 10.dp),
        onValueChange = {
            v = it
        })
    return v
}