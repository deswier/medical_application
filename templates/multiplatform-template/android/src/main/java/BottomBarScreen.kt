import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Document : BottomBarScreen(
        route = "Document",
        title = "Document",
        icon = Icons.Default.Notifications
    )

    object Profile : BottomBarScreen(
        route = "Profile",
        title = "Profile",
        icon = Icons.Default.Person
    )

    object Result : BottomBarScreen(
        route = "Result",
        title = "Result tests",
        icon = Icons.Default.Done
    )
}