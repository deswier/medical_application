package tools

import androidx.compose.ui.graphics.Color
import com.myapplication.model.Note

fun getResultColor(item: Note, normalResult: Color, notNormalResult: Color): Color {
    return if (item.isNormalResult) normalResult
    else notNormalResult
}