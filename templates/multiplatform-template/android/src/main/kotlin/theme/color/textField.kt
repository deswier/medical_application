package theme.color

import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import theme.BluePastel
import theme.DarkBlue
import theme.darkAndLightTheme.TypeTheme

@Composable
public fun getTextFieldColors(): TextFieldColors {
    return if (TypeTheme.isDarkTheme()) textFieldDarkTheme()
    else textFieldLightTheme()
}

@Composable
private fun textFieldLightTheme(): TextFieldColors {
    return TextFieldDefaults.textFieldColors(
        disabledTextColor = onBackgroundLight,
        backgroundColor = backGroundLight,
        focusedIndicatorColor = DarkBlue, //hide the indicator
        unfocusedIndicatorColor = BluePastel,
        textColor = onBackgroundLight
    )
}

@Composable
private fun textFieldDarkTheme(): TextFieldColors {
    return TextFieldDefaults.textFieldColors(
        disabledTextColor = onBackgroundDark,
        backgroundColor = backGroundDark,
        focusedIndicatorColor = DarkBlue, //hide the indicator
        unfocusedIndicatorColor = Color(0xFF032436),
        textColor = onBackgroundDark
    )
}