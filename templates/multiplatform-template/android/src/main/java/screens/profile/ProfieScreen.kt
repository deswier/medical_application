import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.myapplication.exception.DataException
import com.myapplication.model.FullName
import com.myapplication.model.Profile
import screens.profile.ImagePicker
import screens.profile.ImagePicker2
import theme.ImagePickerTheme


@Composable
fun ProfileScreen(profile: Profile) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        // .background(Color.Blue),
        // contentAlignment = Alignment.Center
    ) {
        var fName by rememberSaveable { mutableStateOf(profile.name.firstName) }
        var sName by rememberSaveable { mutableStateOf(profile.name.secondName) }

        TextField(
            value = fName,
            singleLine = true,
            modifier = Modifier.width(200.dp).padding(10.dp, 190.dp),
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
            value = sName,
            singleLine = true,
            modifier = Modifier.width(200.dp).padding(10.dp, 250.dp),
            onValueChange = {
                try {
                    sName = it
                    profile.name = FullName(profile.name.firstName, it)
                } catch (e: DataException) {
                    //TODO name incorrect
                }
            }
        )
        var openSetGender = remember { mutableStateOf(false) }
        var gender = remember { mutableStateOf(profile.gender.toString()) }

        TextField(
            value = gender.value,
            singleLine = true,
            modifier = Modifier.width(70.dp).padding(10.dp, 300.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent, //hide the indicator
                unfocusedIndicatorColor = Color.Green
            ),
            onValueChange = {
                openSetGender.value = true;
            }
        )

        IconButton(modifier = Modifier.padding(70.dp, 300.dp).size(24.dp),
            onClick = {
                if(gender.value == "M") gender.value="F"
                else gender.value="M"
            }) {
            Icon(

                Icons.Filled.Edit,
                "contentDescription",
                tint = Color.Blue
            )
        }
    }
    ImagePickerTheme {
        ImagePicker(profile)
    }
}

@Composable
fun iconGender(x: Dp, y: Dp, size: Dp) {
    val navController = rememberNavController()
    IconButton(modifier = Modifier.padding(x, y).size(size),
        onClick = {
            //   newResultScreen()
        }) {
        Icon(

            Icons.Filled.Add,
            "contentDescription",
            tint = Color.Green
        )
    }
}
//@Composable
//fun changeGender(g:String): String {
//    return if(g == "M") "F"
//    else "M"
//}

