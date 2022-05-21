import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.myapplication.model.Profile
import screens.autorize.registration
import screens.navigation.BottomBarScreen
import screens.navigation.BottomNavGraph
import theme.color.appTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var profile = remember {
        mutableStateOf(Profile())
    }
    appTheme {
        if (profile.value.isEmptyProfile) registration(navController)
        else
            Scaffold(bottomBar = { BottomBar(navController = navController) }) {
                BottomNavGraph(navController = navController, profile)
            }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Document, BottomBarScreen.Result, BottomBarScreen.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen ->
            AddItem(
                screen = screen, currentDestination = currentDestination, navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen, currentDestination: NavDestination?, navController: NavHostController
) {
    BottomNavigationItem(label = {
        Text(text = screen.title)
    }, icon = {
        Icon(
            imageVector = screen.icon, contentDescription = "Navigation Icon"
        )
    }, selected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true, unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled), onClick = {
        navController.navigate(screen.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    })
}