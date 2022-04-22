package theme.darkAndLightTheme

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import screens.navigation.BottomBarScreen


@Composable
fun radioButtonTheme(typeOfThemeApp: String, navController: NavHostController) {
    val dark = VariationOfTheme.dark
    val light = VariationOfTheme.light
    val system = VariationOfTheme.system
    val radioOptions = listOf(dark, light, system)
    val textType = remember { mutableStateOf(typeOfThemeApp) }
    val bool = remember { mutableStateOf(false) }
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
                    RadioButton(
                        selected = (text == selectedOption), modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {
                            //todo save state of theme in file(xml?)
                            onOptionSelected(text)
                            textType.value = text
                            bool.value = true
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

@Composable
private fun setTheme(type: String) {
    TypeTheme.setTheme(type)
}