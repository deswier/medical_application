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
        title = "Длкументы",
        icon = Icons.Default.Notifications
    )

    object Profile : BottomBarScreen(
        route = "Profile",
        title = "Профиль",
        icon = Icons.Default.Person
    )

    object Result : BottomBarScreen(
        route = "Result",
        title = "Результаты",
        icon = Icons.Default.Done
    )
}