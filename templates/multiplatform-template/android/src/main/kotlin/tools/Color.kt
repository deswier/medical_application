package tools

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.myapplication.model.Note
import theme.darkAndLightTheme.TypeTheme
import theme.darkAndLightTheme.darkAndLightColors.backGroundDark
import theme.darkAndLightTheme.darkAndLightColors.backGroundLight
import theme.darkAndLightTheme.darkAndLightColors.onBackgroundDark
import theme.darkAndLightTheme.darkAndLightColors.onBackgroundLight

fun getResultColor(item: Note, normalResult: Color, notNormalResult: Color): Color {
    return if (item.isNormalResult) normalResult
    else notNormalResult
}

@Composable
fun getTextColor(): Color {
    return if (TypeTheme.isDarkTheme()) onBackgroundDark
    else onBackgroundLight
}

@Composable
fun getBackgroundColor(): Color {
    return if (TypeTheme.isDarkTheme()) backGroundDark
    else backGroundLight
}
