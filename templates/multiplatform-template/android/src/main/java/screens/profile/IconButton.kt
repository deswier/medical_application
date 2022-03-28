package screens.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.navigation.compose.rememberNavController

@Composable
fun iconButton(x: Dp, y: Dp, size: Dp) {
    val navController = rememberNavController()
    IconButton(modifier = Modifier.padding(x, y).size(size),
        onClick = {
            //   newResultScreen()
        }) {
        Icon(

            Icons.Filled.Add,
            "contentDescription",
            tint = Color.Green
        )
    }
}