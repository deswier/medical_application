package theme.color

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import theme.TypeTheme.Companion.isDarkTheme

@Composable
fun AppTheme(
    darkTheme: Boolean = isDarkTheme(), content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        content = content
    )
}