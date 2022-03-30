import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import screens.profile.iconButton

@Composable
fun BottomNewResGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = iconButton(50.dp,50.dp,25.dp).toString()
    ) {
        composable(route = BottomBarScreen.Document.route) {
            documentScreen()
        }


    }
}