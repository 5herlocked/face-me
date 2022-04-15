package com.faceme.faceme.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object FaceMeDestinations {
    const val HOME_ROUTE = "home"
    const val USER_LIST = "user_list"
    const val EVENT_HISTORY = "event_history"
    const val SETTINGS = "settings"
}

class FaceMeNavigationActions(navController: NavHostController) {

    val navigateToSettings: () -> Unit = {
        navController.navigate(FaceMeDestinations.SETTINGS) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToEventHistory: () -> Unit = {
        navController.navigate(FaceMeDestinations.EVENT_HISTORY) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToUserList: () -> Unit = {
        navController.navigate(FaceMeDestinations.USER_LIST) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToHome: () -> Unit = {
        navController.navigate(FaceMeDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true

            restoreState = true
        }
    }
}
