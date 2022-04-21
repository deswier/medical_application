import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myapplication.exception.DataException
import com.myapplication.model.FullName
import com.myapplication.model.Profile
import screens.navigation.BottomBarScreen
import screens.profile.imagePicker
import theme.color.AppTheme
import theme.color.VariationOfTheme
import theme.color.getTextColor
import theme.color.getTextFieldColors
import theme.darkAndLightTheme.TypeTheme
import theme.imagePickerTheme
import theme.saveButton
import tools.datePickerTextField

@Composable
fun profileScreen(navController: NavHostController, profile: Profile) {
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
    val type = TypeTheme.getTypeTheme()
    val typeOfThemeApp = remember { mutableStateOf(type) }

    AppTheme {
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
                    colors = getTextFieldColors(),
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
                    colors = getTextFieldColors(),
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
                        colors = getTextFieldColors(),
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
                val context = LocalContext.current
                if (editProfile.value) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = saveButton,
                                contentColor = getTextColor()
                            ),
                            elevation = null,
                            enabled = editProfile.value,
                            onClick = {
                                //todo update in database
                                editProfile.value = false
                                Toast.makeText(context, "Сохранено", Toast.LENGTH_LONG).show()
                            }
                        ) {
                            Text("Сохранить", fontStyle = FontStyle.Normal, fontSize = 15.sp)
                        }
                    }
                }
                SimpleRadioButtonComponent(typeOfThemeApp.value, navController)

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

@Composable
fun setTheme(type: String) {
    TypeTheme.setTheme(type)
}


@Composable
fun SimpleRadioButtonComponent(typeOfThemeApp: String, navController: NavHostController) {
    val dark = VariationOfTheme.dark
    val light = VariationOfTheme.light
    val system = VariationOfTheme.system
    val radioOptions = listOf(dark, light, system)
    var textType = remember { mutableStateOf(typeOfThemeApp) }
    var bool = remember { mutableStateOf(false) }
    setTheme(typeOfThemeApp)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(typeOfThemeApp) }
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) }
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    val context = LocalContext.current
                    RadioButton(
                        selected = (text == selectedOption), modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {
                            //todo save state of theme in file(xml?)
                            onOptionSelected(text)
                            textType.value = text
                            bool.value = true
                            //  Toast.makeText(context, textType, Toast.LENGTH_SHORT).show()
                        }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    if (bool.value) {
                        setTheme(textType.value)
                        Toast.makeText(LocalContext.current, textType.value, Toast.LENGTH_SHORT).show()
                        bool.value = false

                        navController.navigate(BottomBarScreen.Profile.route)
                    }
                }
            }
        }
    }
}