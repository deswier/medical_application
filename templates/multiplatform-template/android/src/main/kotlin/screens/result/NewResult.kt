import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myapplication.model.Note
import factory.RequestFactory.noteService
import factory.call
import screens.outlinedTextFieldValidation
import theme.color.appTheme
import theme.color.notActiveButton
import theme.color.saveButton
import tools.datePickerOutlined
import tools.getTextColor
import java.time.LocalDate
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun newResultScreen(navController: NavHostController) {
    appTheme {
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
                        Text("Healthynetic", fontSize = 22.sp, modifier = Modifier.padding(horizontal = 20.dp))
                        Spacer(Modifier.weight(1f, true))

                    }
                },
            ) {
                ProvideTextStyle(
                    TextStyle(color = getTextColor())
                ) {
                    var lab by rememberSaveable { mutableStateOf("") }
                    var test by rememberSaveable { mutableStateOf("") }
                    var result by rememberSaveable { mutableStateOf("") }
                    var unit by rememberSaveable { mutableStateOf("") }
                    var referenceRange by rememberSaveable { mutableStateOf("") }
                    var comment by rememberSaveable { mutableStateOf("") }
                    var date by rememberSaveable { mutableStateOf(Calendar.getInstance()) }
                    val widthField = 350.dp
                    val context = LocalContext.current
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(widthField)
                            .padding(30.dp, 0.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        date = datePickerOutlined(
                            LocalContext.current,
                            date
                        )

                        outlinedTextFieldValidation(
                            value = test,
                            onValueChange = {
                                test = it
                            },
                            label = { Text(text = "Test name") },
                            modifier = Modifier.fillMaxWidth()
                        )


                              Row() {
                            outlinedTextFieldValidation(
                                value = result,
                                onValueChange = {
                                    result = it
                                },
                                label = { Text(text = "Result") },
                                modifier = Modifier.width(200.dp)
                            )

                            OutlinedTextField(value = unit,
                                label = { Text("Unit") },
                                singleLine = true,
                                modifier = Modifier.width(widthField - 200.dp).padding(0.dp, 8.dp),
                                onValueChange = {
                                    unit = it
                                })
                        }
                        Row() {
                            outlinedTextFieldValidation(
                                value = referenceRange,
                                onValueChange = {
                                    referenceRange = it
                                },
                                label = { Text(text = "Reference range") },
                                modifier = Modifier.width(200.dp)
                            )
                            OutlinedTextField(value = unit,
                                enabled = false,
                                singleLine = true,
                                modifier = Modifier.width(widthField - 200.dp).padding(0.dp, 15.dp),
                                onValueChange = {
                                })
                        }    
                    lab = fieldInput(lab, "Lab", widthField)
                    comment = fieldInput(comment, "Comment", widthField)
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = GrassGreen, contentColor = Color.Black),
                        elevation = null,
                        enabled = getEnabledSave(test, result, referenceRange),
                        onClick = {
                            val note = Note(
                                UUID.randomUUID(),
                                lab,
                                test,
                                Date(),
                                result,
                                referenceRange,
                                unit,
                                comment
                            )

                            Log.i(javaClass.simpleName, "Saving note: ${note}")

                            noteService.createNote(note).call()
                            Toast.makeText(context, "Добавлено", Toast.LENGTH_LONG).show()
                            }
                        ) {
                            Text("Сохранить", fontStyle = FontStyle.Normal, fontSize = 15.sp)
                        }
                                 
                    }
                }
            }
        }
    }
}

fun getEnabledSave(test: String, result: String, referenceRange: String): Boolean {
    return test != "" && referenceRange != "" && result != ""
}

@Composable
fun fieldInput(
    text: String, label: String, widthField: Dp
): String {
    var v by rememberSaveable { mutableStateOf(text) }
    OutlinedTextField(value = v,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.width(widthField).clip(RoundedCornerShape(3.dp)).padding(top = 10.dp),
        onValueChange = {
            v = it
        })
    return v
}