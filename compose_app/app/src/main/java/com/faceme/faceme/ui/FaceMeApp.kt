package com.faceme.faceme.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.faceme.faceme.ui.theme.FaceMeTheme
import com.faceme.faceme.utils.WindowSize
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun FaceMeApp (
    windowSize: WindowSize
) {
    FaceMeTheme {
        val systemUiController = rememberSystemUiController()
        val darkIcons = MaterialTheme.colors.isLight
        SideEffect {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
        }

        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            FaceMeNavigationActions(navController)
        }

        val coroutineScope = rememberCoroutineScope()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: FaceMeDestinations.HOME_ROUTE

        val isExpandedScreen = windowSize == WindowSize.Expanded
        val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)

        ModalDrawer(
            drawerContent = {
                AppDrawer(
                    currentRoute = currentRoute,
                    navigateToHome = navigationActions.navigateToHome,
                    navigateToUserList = navigationActions.navigateToUserList,
                    navigateToEventHistory = navigationActions.navigateToEventHistory,
                    navigateToSettings = navigationActions.navigateToSettings,
                    closeDrawer = { coroutineScope.launch { (sizeAwareDrawerState as DrawerState).close() } },
                    modifier = Modifier.padding(8.dp)
                )
            },
            drawerState = sizeAwareDrawerState as DrawerState,
            // Only enable opening the drawer via gestures if the screen is not expanded
            gesturesEnabled = !isExpandedScreen
        ) {

        }
    }
}

/**
 * Determine the content padding to apply to the different screens of the app
 */
@Composable
fun rememberContentPaddingForScreen(additionalTop: Dp = 0.dp) =
    WindowInsets.systemBars
        .only(WindowInsetsSides.Bottom)
        .add(WindowInsets(top = additionalTop))
        .asPaddingValues()

/*
 * Determine the drawer state to pass to modal
 */
@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): Any {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    return if (!isExpandedScreen) {
        // if we want to allow showing the drawer, we use a real
        // predefined drawerState
        drawerState
    } else {
        // If we don't want to allow the drawer to be shown, we provide a default drawer state
        // that is locked closed. This is intentionally forgotten, because we
        // don't want to keep track of any changes and always keep it closed
        DrawerState(DrawerValue.Closed)
    }
}
