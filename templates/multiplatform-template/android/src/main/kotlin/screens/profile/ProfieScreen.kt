import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.exception.DataException
import com.myapplication.model.FullName
import com.myapplication.model.Profile
import screens.profile.imagePicker
import theme.BluePastel
import theme.DarkBlue
import theme.GrassGreen
import theme.imagePickerTheme
import tools.datePickerTextField

@Composable
fun profileScreen(profile: Profile) {
    val widthField = 300.dp
    val editProfile = remember { mutableStateOf(false) }
    var fName by rememberSaveable { mutableStateOf(profile.name.firstName) }
    var sName by rememberSaveable { mutableStateOf(profile.name.secondName) }
    var date by rememberSaveable { mutableStateOf(profile.dateOfBirth) }
    val gender = remember { mutableStateOf(profile.genderToString) }
    Scaffold(
        topBar = {
            TopAppBar {
                Text("Healthynetic", fontSize = 22.sp, modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.weight(1f, true))
                IconButton(
                    onClick = {
                        editProfile.value = true
                    },
                    Modifier.width(50.dp)
                ) {
                    Icon(
                        Icons.Filled.Edit,
                        "contentDescription",
                    )
                }
            }
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 10.dp).width(widthField),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            imagePickerTheme {
                imagePicker(profile, editProfile.value)
            }

            TextField(
                value = fName,
                modifier = Modifier.width(widthField),
                label = { Text(text = "Имя") },
                singleLine = true,
                enabled = editProfile.value,
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = DarkBlue,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = DarkBlue, //hide the indicator
                    unfocusedIndicatorColor = BluePastel
                ),
                onValueChange = {
                    try {
                        fName = it
                        profile.name = FullName(it, sName)
                    } catch (e: DataException) {
                        //TODO name incorrect
                    }
                }
            )
            TextField(
                value = (sName),
                modifier = Modifier.width(widthField),
                label = { Text(text = "Фамилия") },
                singleLine = true,
                enabled = editProfile.value,
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = DarkBlue,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = DarkBlue, //hide the indicator
                    unfocusedIndicatorColor = BluePastel
                ),
                onValueChange = {
                    try {
                        sName = it
                        profile.name = FullName(profile.name.firstName, it)
                    } catch (e: DataException) {
                        //TODO name incorrect
                    }
                }
            )

            date = datePickerTextField(
                context = LocalContext.current,
                calendar = date,
                enabled = editProfile.value,
                width = widthField,
                label = "Дата рождения"
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    disabledBackgroundColor = Color.White,
                    disabledContentColor = DarkBlue,
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                ),
                elevation = null,
                enabled = editProfile.value,
                onClick = {
                    if (gender.value == "Мужчина") gender.value = "Женщина"
                    else gender.value = "Мужчина"
                }
            ) {
                Text(gender.value)
            }

            if (editProfile.value) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = GrassGreen, contentColor = Color.Black),
                        elevation = null,
                        enabled = editProfile.value,
                        onClick = {
                            editProfile.value = false
                        }
                    ) {
                        Text("Save", fontStyle = FontStyle.Normal, fontSize = 15.sp)
                    }
                }
            }
        }
    }
}