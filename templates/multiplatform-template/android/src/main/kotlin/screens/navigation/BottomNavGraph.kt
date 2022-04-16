package screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.myapplication.model.FullName
import com.myapplication.model.Profile
import newResultScreen
import profileScreen
import screens.documentScreen
import screens.result.resultScreen
import screens.result.showResultScreen
import java.util.*

@Composable
fun BottomNavGraph(navController: NavHostController) {
    val profile = remember {
        val cal = Calendar.getInstance()
        cal.set(1999, 5, 13)
        Profile(FullName("Alina", "Mikhaleva"), cal, 'F', null)
    }
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Result.route
    ) {
        composable(route = BottomBarScreen.Document.route) {
            documentScreen(navController)
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

//        composable(route = "adderFolder") {
//            folderFromDocumentScreen(navController)
//        }
//
//        composable(route = "adderFile") {
//            newResultScreen(navController)
//        }

//        composable(
//            route = "${MainDestinations.OPEN_FOLDER}/{${MainDestinations.PARENT_FOLDER}}",
//            arguments = listOf(navArgument(MainDestinations.PARENT_FOLDER) {
//                type =
//                    NavType.StringType
//                //here will be uuid
//            })
//        ) { backStackEntry ->
//            val arguments = requireNotNull(backStackEntry.arguments)
//            val resultCardId = arguments.getString(MainDestinations.RESULT_CARD,null)
//            if (resultCardId != null)
//            //    CardDialog(resultCardId, upPress, {}, {})
//                showResultScreen(navController,resultCardId)
//        }

        composable(
            route = "${MainDestinations.SHOW_RESULT}/{${MainDestinations.RESULT_CARD}}",
            arguments = listOf(navArgument(MainDestinations.RESULT_CARD) {
                type =
                    NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val resultCardId = arguments.getString(MainDestinations.RESULT_CARD, null)
            if (resultCardId != null)
            //    CardDialog(resultCardId, upPress, {}, {})
                showResultScreen(navController,resultCardId)
        }
    }
}