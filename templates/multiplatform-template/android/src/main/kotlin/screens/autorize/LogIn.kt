package screens.autorize

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import com.myapplication.model.FullName
import com.myapplication.model.Profile
import factory.RequestFactory
import theme.color.appTheme
import tools.getBackgroundColor
import tools.getTextColor
import java.util.*

@Composable
fun LogIn(navController: NavHostController, profile: MutableState<Profile>) {
    val log = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val successLogIn = remember { mutableStateOf(false) }

    appTheme {
        Scaffold(
            topBar = {
                TopAppBar {
                    Text("Healthynetic", fontSize = 22.sp, modifier = Modifier.padding(horizontal = 20.dp))
                    Spacer(Modifier.weight(1f, true))
                }
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                logo(LocalContext.current)
                TextField(
                    label = { Text(text = "Логин") },
                    modifier = Modifier.padding(top = 20.dp),
                    value = log.value,
                    onValueChange = {
                        log.value = it
                    }
                )
                TextField(
                    label = { Text(text = "Пароль") },
                    value = password.value,
                    //  value = (getHiddenPassword(password)),
                    //  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = {
                        password.value = it
                    }
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth().padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = getBackgroundColor(),
                        contentColor = getTextColor()
                    ),
                    enabled = getEnabledLogIn(log, password),
                    onClick = {
                        //todo
                        Log.i(javaClass.simpleName, "Log in")
                        RequestFactory.login = log.value;
                        RequestFactory.password = password.value
                        profile.value = Profile(FullName("Alina", "Mikhaleva"), Calendar.getInstance(), 'F', null)
                        successLogIn.value = true
                    }
                ) {
                    Text("Войти", fontStyle = FontStyle.Normal, fontSize = 15.sp)
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth().padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = getBackgroundColor(),
                        contentColor = getTextColor()
                    ),
                    onClick = {
                        //todo
                        Log.i(javaClass.simpleName, "Registration")
                        navController.navigate("registration")
                    }
                ) {
                    Text("Регистрация", fontStyle = FontStyle.Normal, fontSize = 15.sp)
                }
            }
        }
    }
}

fun getEnabledLogIn(log: MutableState<String>, password: MutableState<String>): Boolean {
    return log.value.length >= 4 && password.value.length >= 5
}

fun getHiddenPassword(password: MutableState<String>): String {
    var res = ""
    repeat(password.value.length) {
        res += "*"
    }
    return res;
}

@Composable
fun logo(context: Context) {
    val resId = context.resources.getIdentifier("logo", "drawable", context.packageName)
    val img = context.getDrawable(resId)
    if (img != null) {
        Image(
            bitmap = img.toBitmap(250, 250).asImageBitmap(),
            contentDescription = "Logo",
            modifier = Modifier.size(250.dp)
                .clip(CircleShape)
                .border(width = 3.dp, color = Color.White, shape = CircleShape)
        )
    }
}