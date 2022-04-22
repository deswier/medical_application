package theme.darkAndLightTheme.darkAndLightColors

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import theme.DarkColorPalette
import theme.LightColorPalette
import theme.Shapes
import theme.Typography
import theme.darkAndLightTheme.TypeTheme


@Composable
fun imagePickerTheme(
    darkTheme: Boolean = TypeTheme.isDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun bottomNavBarDemoTheme(
    darkTheme: Boolean = TypeTheme.isDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}