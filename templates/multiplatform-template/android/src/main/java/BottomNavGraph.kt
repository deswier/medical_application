import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.myapplication.model.FullName
import com.myapplication.model.Profile
import com.myapplication.tools.DateParser
import screens.MainDestinations
import screens.documentScreen
import screens.showResultScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    val profile = remember {
        Profile(FullName("Evgeniy", "Ignatenko"), DateParser.convertToLocalDate("20-02-2000"), 'M')
    }
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Result.route
    ) {
        composable(route = BottomBarScreen.Document.route) {
            documentScreen()
        }
        composable(route = BottomBarScreen.Result.route) {
            resultScreen(navController)
        }
        composable(route = BottomBarScreen.Profile.route) {
            profileScreen(profile)
        }
        composable(route = "adderResult") {
            newResultScreen(navController)
        }

        composable(
            route = "${MainDestinations.SHOW_RESULT}/{${MainDestinations.RESULT_CARD}}",
            arguments = listOf(navArgument(MainDestinations.RESULT_CARD) {
                type =
                    NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val resultCardId = arguments.getString(MainDestinations.RESULT_CARD,null)
            if (resultCardId != null)
            //    CardDialog(resultCardId, upPress, {}, {})
            showResultScreen(navController,resultCardId)
        }
    }
}