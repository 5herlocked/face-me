package com.faceme.faceme.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.faceme.faceme.data.AppContainer
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
    startDestination: String = FaceMeDestinations.HOME_ROUTE,
    appContainer: AppContainer
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(FaceMeDestinations.HOME_ROUTE) {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(appContainer.eventsRepository)
            )
            HomeRoute(
                homeViewModel = homeViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }
        composable(FaceMeDestinations.EVENT_HISTORY) {
            val eventHistoryViewModel : EventHistoryViewModel = viewModel(
                factory = EventHistoryViewModel.provideFactory(appContainer.eventsRepository)
            )
            EventHistoryRoute(
                eventHistoryViewModel = eventHistoryViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }
        composable(FaceMeDestinations.USER_LIST) {

        }

        composable(FaceMeDestinations.SETTINGS) {

        }
//        composable(FaceMeDestinations.USER_LIST) {
//            // TODO: Correct this and fill in the appropriate route
//            HomeRoute(
//                homeViewModel = HomeViewModel(),
//                isExpandedScreen = isExpandedScreen,
//                openDrawer = openDrawer
//            )
//        }
//        composable(FaceMeDestinations.SETTINGS) {
//            // TODO: Correct this and fill in the appropriate route
//            HomeRoute(
//                homeViewModel = HomeViewModel(),
//                isExpandedScreen = isExpandedScreen,
//                openDrawer = openDrawer
//            )
//        }
    }
}