import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Result.route
    ) {
        composable(route = BottomBarScreen.Document.route) {
            DocumentScreen()
        }
        composable(route = BottomBarScreen.Result.route) {
            ResultScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }

    }
}