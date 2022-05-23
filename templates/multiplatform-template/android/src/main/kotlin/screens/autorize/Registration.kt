package screens.autorize

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myapplication.exception.DataException
import com.myapplication.model.FullName
import theme.color.appTheme
import theme.color.getTextFieldColors
import tools.getBackgroundColor
import tools.getTextColor


@Composable
fun registration(navController: NavHostController) {
    val log = remember { mutableStateOf("") }
    val password1 = remember { mutableStateOf("") }
    val password2 = remember { mutableStateOf("") }
    val fName = remember { mutableStateOf("") }
    val sName = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("Мужской") }

    val successLogIn = remember { mutableStateOf(false) }
    val openDialogEqualsPasswords = remember { mutableStateOf(false) }
    val screenReg = remember { mutableStateOf(true) }
    val screenProfile = remember { mutableStateOf(false) }
    val openDialogAlertName = remember { mutableStateOf(false) }

    appTheme {
        Scaffold(
            topBar = {
                TopAppBar {
                    if (screenProfile.value)
                        IconButton(
                            onClick = {
                                screenProfile.value = false
                                screenReg.value = true
                            },
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
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (screenReg.value) {
                    logo(LocalContext.current)
                    textField(log, "Логин", modifier = Modifier.padding(top = 20.dp))
                    textField(
                        password1,
                        "Пароль",
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardType = KeyboardType.Password
                    )
                    textField(
                        password2,
                        "Повторите пароль",
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardType = KeyboardType.Password
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth().padding(top = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = getBackgroundColor(),
                            contentColor = getTextColor()
                        ),
                        enabled = getEnabledLogIn(log, password1) && getEnabledLogIn(log, password2),
                        onClick = {
                            //todo
                            if (password2 == password1) {
                                openDialogEqualsPasswords.value = true
                            } else {
                                screenReg.value = false
                                screenProfile.value = true
                            }
                            successLogIn.value = true
                        }
                    ) {
                        Text("Продолжить", fontStyle = FontStyle.Normal, fontSize = 15.sp)
                    }
                } else {
                    textField(fName, "Имя", modifier = Modifier.padding(top = 20.dp))
                    textField(sName, "Фамилия")
                    val age = remember { mutableStateOf(20) }
                    val isErrorAge = remember { mutableStateOf(false) }
                    textFieldAge(age, isErrorAge)
                    Row(modifier = Modifier.width(150.dp)) {
                        TextField(
                            modifier = Modifier.width(100.dp),
                            value = (gender.value),
                            label = { Text(text = "Пол") },
                            singleLine = true,
                            enabled = false,
                            colors = getTextFieldColors(),
                            onValueChange = {
                            }
                        )
                        IconButton(
                            onClick = {
                                if (gender.value == "Мужской") gender.value = "Женский"
                                else gender.value = "Мужской"
                            },
                            Modifier.width(50.dp).padding(top = 20.dp)
                        ) {
                            Icon(
                                Icons.Filled.Refresh, "contentDescription",
                            )
                        }
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth().padding(top = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = getBackgroundColor(),
                            contentColor = getTextColor()
                        ),
                        enabled = fName.value != "" && sName.value != "" && !isErrorAge.value,
                        onClick = {
                            try {
                                val fullName = FullName(fName.value, sName.value)
                            } catch (_: DataException) {
                                openDialogAlertName.value = true
                            }
                            //todo add profile
                        }
                    ) {
                        Text("Продолжить", fontStyle = FontStyle.Normal, fontSize = 15.sp)
                    }
                }
            }
            if (openDialogEqualsPasswords.value) {
                alertDialog(openDialogEqualsPasswords, "Пароли не совпадают")
            }
            if (openDialogAlertName.value) {
                alertDialog(openDialogAlertName, "Имя и фамилия должны содержать только буквы")
            }
        }
    }
}

@Composable
fun alertDialog(openDialog: MutableState<Boolean>, error: String) {
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(text = "Ошибка")
        },
        text = {
            Column() {
                Text(error)
            }
        },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { openDialog.value = false }
                ) {
                    Text("Назад")
                }
            }
        }
    )
}

@Composable
fun textField(
    value: MutableState<String>,
    label: String,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Ascii
) {
    TextField(
        label = { Text(text = label) },
        modifier = modifier,
        value = value.value,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        onValueChange = {
            value.value = it
        }
    )
}

@Composable
fun textFieldAge(
    age: MutableState<Int>,
    isErrorAge: MutableState<Boolean>,
) {
    val a = remember { mutableStateOf(age.value.toString()) }

    Column {
        TextField(
            value = a.value,
            label = { Text("Возраст") },
            onValueChange = {
                a.value = it
                try {
                    val t = Integer.parseInt(a.value)
                    if (t > 120 || t < 0) throw  DataException("Incorrect age")
                    isErrorAge.value = false
                } catch (e: Exception) {
                    isErrorAge.value = true
                }
            },
            trailingIcon = {
                if (isErrorAge.value)
                    Icon(Icons.Filled.Warning, "incorrectAgeInput", tint = MaterialTheme.colors.error)
            },
            singleLine = true,
            isError = isErrorAge.value,
        )
        if (isErrorAge.value) {
            Text(
                text = "",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}