package theme.color

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import theme.darkAndLightTheme.TypeTheme

@Composable
fun appTheme(content: @Composable () -> Unit) {
    val darkTheme = TypeTheme.isDarkTheme()
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        content = content
    )
}