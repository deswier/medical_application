import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myapplication.tools.DateParser
import theme.GrassGreen
import java.util.*

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
                val widthField = 350.dp

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(widthField)
                        .padding(30.dp, 0.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    date = showDatePicker(LocalContext.current)

                    OutlinedTextFieldValidation(
                        value = test,
                        onValueChange = {
                            test = it
                        },
                        label = { Text(text = "Test name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row() {
                        OutlinedTextFieldValidation(
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
                        OutlinedTextFieldValidation(
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

                        }
                    ) {
                        Text("Save", fontStyle = FontStyle.Normal, fontSize = 15.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun showDatePicker(
    context: Context
): String {

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()
    val date = remember { mutableStateOf(DateParser.convertToString(Date())) }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, y: Int, m: Int, d: Int ->
            date.value = DateParser.convertToString(DateParser.convertToDate(d, m, y))
        }, year, month, day
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(16.dp))
        Row() {
            OutlinedTextFieldValidation(
                value = date.value.toString(),
                onValueChange = {
                },
                enabled = false,
                label = { Text(text = "Date") },
            )
            IconButton(
                onClick = { datePickerDialog.show() },
                Modifier.width(50.dp).padding(top=20.dp)
            ) {
                Icon(
                    Icons.Filled.DateRange,
                    "contentDescription",
                )
            }
        }
    }
    return date.value
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
        modifier = Modifier.width(widthField).clip(RoundedCornerShape(3.dp)).padding(0.dp, 10.dp),
        onValueChange = {
            v = it
        })
    return v
}

@Composable
fun OutlinedTextFieldValidation(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(0.8f),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = value.isEmpty(),
    trailingIcon: @Composable (() -> Unit)? = {
        if (isError)
            Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
    },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        disabledTextColor = Color.Black
    )

): Boolean {

    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        OutlinedTextField(
            enabled = enabled,
            readOnly = readOnly,
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = singleLine,
            textStyle = textStyle,
            label = label,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            maxLines = maxLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors
        )
        if (isError) {
            Text(
                text = "Field can't be empty",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
            )
        }
    }
    return isError //RETURN TRUE IF NOT ERROR  /// RETURN CORRECT OR NOT
}
