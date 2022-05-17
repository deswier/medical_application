package screens.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.myapplication.model.Profile
import newResultScreen
import profileScreen
import screens.autorize.LogIn
import screens.documentScreen
import screens.result.resultScreen
import screens.result.showResultScreen
import theme.color.appTheme
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavGraph(navController: NavHostController, profile: MutableState<Profile>) {

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Result.route
    ) {
        composable(route = BottomBarScreen.Document.route) {
            appTheme {
                documentScreen(navController)
            }
        }
        composable(route = BottomBarScreen.Result.route) {
            appTheme {
                resultScreen(navController)
            }
        }
        composable(route = BottomBarScreen.Profile.route) {
            appTheme {
                profileScreen(navController, profile.value)
            }
        }
        composable(route = "adderResult") {
            newResultScreen(navController)
        }

        composable(route = "logIn") {
            appTheme {
                LogIn(navController, profile)
            }
        }

        composable(
            route = "${MainDestinations.SHOW_RESULT}/{${MainDestinations.RESULT_CARD}}",
            arguments = listOf(navArgument(MainDestinations.RESULT_CARD) {
                type =
                    NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val resultCardId = UUID.fromString(arguments.getString(MainDestinations.RESULT_CARD, null))
            if (resultCardId != null)
                showResultScreen(navController, resultCardId)
        }
    }
}