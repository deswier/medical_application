package screens.fields.button

import ResultScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun closeButton(x: Dp, y: Dp, size: Dp) {
    IconButton(modifier = Modifier.padding(x, y).size(size),
        onClick = {
            //ResultScreen()
        }) {
        Icon(

            Icons.Filled.Close,
            "contentDescription",
            tint = Color.Red
        )
    }
}
