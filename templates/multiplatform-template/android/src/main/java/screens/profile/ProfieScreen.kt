import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun profileScreen(profile: Profile) {
    val editProfile = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth().fillMaxSize()
    ) {
        IconButton(modifier = Modifier.padding(20.dp).size(24.dp),
            onClick = {
                editProfile.value = true
            }) {
            Icon(
                Icons.Filled.Edit,
                "contentDescription",
                tint = Color.Blue
            )
        }
    }

    val paddingX = 10.dp
    val paddingY = 200.dp
    var fName by rememberSaveable { mutableStateOf(profile.name.firstName) }
    var sName by rememberSaveable { mutableStateOf(profile.name.secondName) }
    var date by rememberSaveable { mutableStateOf(profile.dateOfBirth) }
    val gender = remember { mutableStateOf(profile.genderToString) }
    Column(

        modifier = Modifier
            .fillMaxSize().padding(paddingX, paddingY),
    ) {
        // date = showDatePicker(LocalContext.current)
        TextField(
            value = fName,
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
                modifier = Modifier
                    .fillMaxWidth()
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

    imagePickerTheme {
        imagePicker(profile, editProfile.value)
    }
}