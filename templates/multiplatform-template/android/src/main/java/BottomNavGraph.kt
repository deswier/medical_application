import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myapplication.model.FullName
import com.myapplication.model.Profile
import com.myapplication.tools.DateParser

@Composable
fun BottomNavGraph(navController: NavHostController) {
    val profile = remember {
        //XmlHelper.read()
        Profile(FullName("Evgeniy","Ignatenko"), DateParser.parseStringToDate("20-02-2000"), 'M')
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
    }
}