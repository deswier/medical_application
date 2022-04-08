import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myapplication.model.Note
import com.myapplication.tools.DateParser
import theme.GrassGreen
import java.util.*

@Composable
fun showResultScreen(navController: NavHostController, uuid: String) {
    val card = getCardOfResult(uuid)
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp, 0.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    var text = DateParser.convertToTextDate(card.date)
                    if (card.lab != null && card.lab != "null")
                        text = text + " in " + card.lab
                    Text(
                        text,
                        modifier = Modifier.padding(top = 50.dp)
                    )
                    Text(card.comment, modifier = Modifier.padding(top = 50.dp))
                }
            }
        }
    }
}

fun getCardOfResult(uuid: String): Note {
    //TODO with database
    return Note(
        UUID.fromString(uuid), "Invitro",
        "Fe", Date(), "9", "9-30.4", "mm/l", "i love banana"
    )
}