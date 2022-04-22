package screens.documents.folder

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun newFolderScreen(navController: NavController) {
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
                    Text("Healthynetic", fontSize = 22.sp, modifier = Modifier.padding(horizontal = 20.dp))
                    Spacer(Modifier.weight(1f, true))

                }
            },
        ) {

//            var name by rememberSaveable { mutableStateOf("") }
//            val widthField = 350.dp

//            Column(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(widthField)
//                    .padding(30.dp, 0.dp)
//                    .verticalScroll(rememberScrollState())
//            ) {
//                date = datePickerOutlined(
//                    LocalContext.current,
//                    date
//                )
//
//                outlinedTextFieldValidation(
//                    value = test,
//                    onValueChange = {
//                        test = it
//                    },
//                    label = { Text(text = "Test name") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//
//            }
        }
    }
}