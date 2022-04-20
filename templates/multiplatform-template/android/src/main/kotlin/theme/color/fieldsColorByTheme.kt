package theme.color

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
public fun getTextColor(): Color {
    return if (isDarkTheme()) onBackgroundDark
    else onBackgroundLight
}

@Composable
public fun getBackgroundColor(): Color {
    return if (isDarkTheme()) backGroundDark
    else backGroundLight
}
