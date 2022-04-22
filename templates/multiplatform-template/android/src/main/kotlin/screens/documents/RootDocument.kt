package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myapplication.storage.TestFolders
import theme.color.DarkBlue

import theme.color.appTheme
import tools.getBackgroundColor

@Composable
fun documentScreen(navController: NavHostController) {
    val expanded = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val folders = remember { TestFolders() }
    appTheme {
        Scaffold(
            topBar = {
                TopAppBar {
                    Text("Healthynetic", fontSize = 22.sp, modifier = Modifier.padding(horizontal = 20.dp))
                    Spacer(Modifier.weight(1f, true))
                    ProvideTextStyle(
                        TextStyle(color = Color.White, fontSize = 8.sp)
                    ) {}
                    Box {
                        Modifier.background(getBackgroundColor())
                        IconButton(
                            onClick = {
                                expanded.value = true
                            }) { Icon(Icons.Filled.Add, "contentDescription") }
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }
                        ) {
                            Text(
                                "Добавить папку",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(10.dp).clickable(onClick = {
                                    openDialog.value = true
                                })
                            )
                            Text(
                                "Добавить файл",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(10.dp).clickable(onClick = {})
                            )
                            Divider()
                            Text(
                                "Настройки",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(10.dp).clickable(onClick = {})
                            )
                        }
                    }
                }
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (openDialog.value) {
                    val folderName = remember { mutableStateOf("") }
                    AlertDialog(
                        modifier = Modifier.width(300.dp),
                        onDismissRequest = {
                            openDialog.value = false
                        },
                        title = { Text(text = "Добавить папку") },
                        text = {
                            Column {
                                Text("Введите название папки")
                                outlinedTextFieldValidation(
                                    value = folderName.value,
                                    onValueChange = {
                                        folderName.value = it
                                    },
                                )
                            }
                        },
                        buttons = {

                            Row(modifier = Modifier.fillMaxWidth()) {

                                Button(
                                    modifier = Modifier.padding(horizontal = 20.dp),
                                    onClick = {
                                        if (folderName.value != "") {
                                            folders.add(folderName.value)
                                            openDialog.value = false
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        disabledBackgroundColor = Color.White,
                                        disabledContentColor = DarkBlue,
                                        backgroundColor = Color.White,
                                        contentColor = Color.Black
                                    ),
                                    elevation = null

                                ) {
                                    Text("Добавить")
                                }

                                Button(
                                    modifier = Modifier.padding(horizontal = 15.dp),
                                    onClick = { openDialog.value = false },
                                    colors = ButtonDefaults.buttonColors(
                                        disabledBackgroundColor = Color.White,
                                        disabledContentColor = DarkBlue,
                                        backgroundColor = Color.White,
                                        contentColor = MaterialTheme.colors.error
                                    ),
                                    elevation = null
                                )
                                {
                                    Text("Отменить")
                                }
                            }
                        }
                    )
                }
                for (item in folders.folders) {
                    Column(
                        modifier = Modifier.fillMaxWidth().height(100.dp).clickable(onClick = {
                            //todo navigate
                            navController.navigate("")

                            //folderFromDocumentScreen(navController, item)
                        })
                    ) {
                        Row() {
                            Icon(
                                Icons.Filled.Email,
                                "folderIcon",
                            )
                            Text(
                                item.name, modifier = Modifier.padding(horizontal = 30.dp)
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun outlinedTextFieldValidation(
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
