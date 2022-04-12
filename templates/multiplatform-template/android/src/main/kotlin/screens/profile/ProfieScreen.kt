import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
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
    val fName = remember { mutableStateOf(profile.name.firstName) }
    val sName = remember { mutableStateOf(profile.name.secondName) }
    val date = remember { mutableStateOf(profile.dateOfBirth) }
    val gender = remember { mutableStateOf(profile.genderToString) }

    var prevFName = profile.name.firstName
    var prevSName = profile.name.secondName
    var prevDate = profile.dateOfBirth
    var prevGender = profile.genderToString


    Scaffold(
        topBar = {
            TopAppBar {
                if (editProfile.value) {
                    editProfile.value = iconButtonTopBar(editProfile.value, Icons.Filled.ArrowBack)
                    fName.value = prevFName
                    sName.value = prevSName
                    date.value = prevDate
                    gender.value = prevGender
                }
                Text("Healthynetic", fontSize = 22.sp, modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.weight(1f, true))
                if (!editProfile.value) {
                    prevFName = fName.value
                    prevSName = sName.value
                    prevDate = date.value
                    prevGender = gender.value
                    editProfile.value = iconButtonTopBar(editProfile.value, Icons.Filled.Edit)
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
                value = fName.value,
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
                        fName.value = it
                        profile.name = FullName(it, sName.value)
                    } catch (e: DataException) {
                        //TODO name incorrect
                    }
                }
            )
            TextField(
                value = (sName.value),
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
                        sName.value = it
                        profile.name = FullName(profile.name.firstName, it)
                    } catch (e: DataException) {
                        //TODO name incorrect
                    }
                }
            )

            date.value = datePickerTextField(
                context = LocalContext.current,
                calendar = date.value,
                enabled = editProfile.value,
                width = widthField,
                label = "Дата рождения"
            )
            Row(
                modifier = Modifier.width(widthField)
            ) {
                TextField(
                    value = (gender.value),
                    modifier = Modifier.width(getWidthWithIconByEnabled(editProfile.value, widthField, 50.dp)),
                    label = { Text(text = "Пол") },
                    singleLine = true,
                    enabled = false,
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = DarkBlue,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = DarkBlue, //hide the indicator
                        unfocusedIndicatorColor = BluePastel
                    ),
                    onValueChange = {
                    }
                )
                if (editProfile.value) {
                    IconButton(
                        onClick = {
                            if (gender.value == "Мужской") gender.value = "Женский"
                            else gender.value = "Мужской"
                        },
                        Modifier.width(50.dp).padding(top = 20.dp)
                    ) {
                        Icon(
                            Icons.Filled.Refresh,
                            "contentDescription",
                        )
                    }
                }
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
                            //todo update in database
//                            editProfile.value = false
//                            prevFName = fName.value
//                            prevSName = sName.value
//                            prevDate = date.value
//                            prevGender = gender.value

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
fun iconButtonTopBar(editProfile: Boolean, icon: ImageVector): Boolean {
    var flagEdit by rememberSaveable { mutableStateOf(editProfile) }

    IconButton(
        onClick = {
            flagEdit = !flagEdit
        },
        Modifier.width(50.dp)
    ) {
        Icon(
            icon,
            "contentDescription"
        )
    }
    return flagEdit
}

fun getWidthWithIconByEnabled(enabled: Boolean, widthField: Dp, iconSize: Dp): Dp {
    return if (enabled) widthField - iconSize
    else {
        widthField
    }
}
