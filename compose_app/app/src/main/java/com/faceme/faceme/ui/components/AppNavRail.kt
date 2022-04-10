package com.faceme.faceme.ui.components

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faceme.faceme.R
import com.faceme.faceme.ui.FaceMeDestinations
import com.faceme.faceme.ui.theme.FaceMeTheme

@Composable
fun FaceMeNavRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    NavigationRail(
        modifier = modifier,
        elevation = 0.dp,
        header = header
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            content()
        }
    }

}

@Composable
fun AppNavRail(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToEventHistory: () -> Unit,
    navigateToUserList: () -> Unit,
    navigateToSettings: () -> Unit
) {
    FaceMeNavRail(
        header = {
            FaceMeIcon(Modifier.padding(top = 8.dp))
        }
    ) {
        NavRailIcon(
            icon = Icons.Filled.Home,
            contentDescription = stringResource(id = R.string.cd_navigate_home),
            isSelected = currentRoute == FaceMeDestinations.HOME_ROUTE,
            action = navigateToHome
        )
        Spacer(modifier = Modifier.height(16.dp))
        NavRailIcon(
            icon = Icons.Filled.Group,
            contentDescription = stringResource(R.string.cd_user_list),
            isSelected = currentRoute == FaceMeDestinations.USER_LIST,
            action = navigateToUserList
        )
        Spacer(modifier = Modifier.height(16.dp))
        NavRailIcon(
            icon = Icons.Filled.List,
            contentDescription = stringResource(R.string.cd_event_history),
            isSelected = currentRoute == FaceMeDestinations.EVENT_HISTORY,
            action = navigateToEventHistory
        )
        Spacer(modifier = Modifier.height(16.dp))
        NavRailIcon(
            icon = Icons.Filled.Group,
            contentDescription = stringResource(R.string.cd_navigate_to_settings),
            isSelected = currentRoute == FaceMeDestinations.SETTINGS,
            action = navigateToSettings
        )

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NavRailIcon(
    icon: ImageVector,
    contentDescription: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        if (isSelected) {
            MaterialTheme.colors.primary.copy(alpha = 0.12f)
        } else {
            Color.Transparent
        }
    )

    Surface(
        selected = isSelected,
        color = backgroundColor,
        onClick = action,
        shape = CircleShape,
        modifier = modifier.size(48.dp)
    ) {
        NavigationIcon(
            icon = icon,
            isSelected = isSelected,
            contentDescription = contentDescription,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Preview("Drawer Contents")
@Preview("Drawer Contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppNavRail() {
    FaceMeTheme() {
        AppNavRail(
            currentRoute = FaceMeDestinations.HOME_ROUTE,
            navigateToHome = { },
            navigateToEventHistory = { },
            navigateToUserList = { },
            navigateToSettings = { }
        )
    }
}