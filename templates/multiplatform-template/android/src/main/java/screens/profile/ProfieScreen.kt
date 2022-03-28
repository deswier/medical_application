import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.*
import com.myapplication.exception.DataException
import com.myapplication.model.FullName
import com.myapplication.model.Profile
import screens.profile.ImagePicker
import theme.*


@Composable
fun ProfileScreen(profile: Profile) {
    var editProfile = remember { mutableStateOf(false) }

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

    var paddingX = 10.dp
    var paddingY = 200.dp
    var fName by rememberSaveable { mutableStateOf(profile.name.firstName) }
    var sName by rememberSaveable { mutableStateOf(profile.name.secondName) }
    var gender = remember { mutableStateOf(profile.genderToString) }
    Column(

        modifier = Modifier
            .fillMaxSize().padding(paddingX, paddingY),
    ) {
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
            Text(gender.value, fontFamily = FontFamily.SansSerif, fontSize = 20.sp)
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

    ImagePickerTheme {
        ImagePicker(profile, 130.dp, 50.dp)
    }
}