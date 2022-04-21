package theme.color

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import theme.darkAndLightTheme.TypeTheme

@Composable
public fun getTextColor(): Color {
    return if (TypeTheme.isDarkTheme()) onBackgroundDark
    else onBackgroundLight
}

@Composable
public fun getBackgroundColor(): Color {
    return if (TypeTheme.isDarkTheme()) backGroundDark
    else backGroundLight
}
