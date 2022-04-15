package com.faceme.faceme.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.faceme.faceme.ui.event_history.EventHistoryRoute
import com.faceme.faceme.ui.event_history.EventHistoryViewModel
import com.faceme.faceme.ui.home.HomeRoute
import com.faceme.faceme.ui.home.HomeViewModel

@Composable
fun FaceMeNavGraph(
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = FaceMeDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(FaceMeDestinations.HOME_ROUTE) {
            HomeRoute(
                homeViewModel = HomeViewModel(),
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }
        composable(FaceMeDestinations.EVENT_HISTORY) {
            EventHistoryRoute(
                eventHistoryViewModel = EventHistoryViewModel(),
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }
        composable(FaceMeDestinations.USER_LIST) {
            // TODO: Correct this and fill in the appropriate route
            HomeRoute(
                homeViewModel = HomeViewModel(),
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }
        composable(FaceMeDestinations.SETTINGS) {
            // TODO: Correct this and fill in the appropriate route
            HomeRoute(
                homeViewModel = HomeViewModel(),
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }
    }
}