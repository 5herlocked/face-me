package com.faceme.faceme.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faceme.faceme.R
import com.faceme.faceme.ui.components.FaceMeIcon
import com.faceme.faceme.ui.components.NavigationIcon

@Composable
fun AppDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToUserList: () -> Unit,
    navigateToEventHistory: () -> Unit,
    navigateToSettings: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        FaceMeLogo(Modifier.padding(16.dp))
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
        DrawerButton(
            icon = Icons.Filled.Home,
            label = stringResource(id = R.string.home_title),
            isSelected = currentRoute == FaceMeDestinations.HOME_ROUTE,
            action = {
                navigateToHome()
                closeDrawer()
            }
        )
        DrawerButton(
            icon = Icons.Filled.Group,
            label = stringResource(id = R.string.user_list),
            isSelected = currentRoute == FaceMeDestinations.USER_LIST,
            action = {
                navigateToUserList()
                closeDrawer()
            }
        )
        DrawerButton(
            icon = Icons.Filled.List,
            label = stringResource(id = R.string.event_history),
            isSelected = currentRoute == FaceMeDestinations.EVENT_HISTORY,
            action = {
                navigateToEventHistory()
                closeDrawer()
            }
        )
        DrawerButton(
            icon = Icons.Filled.Settings,
            label = stringResource(id = R.string.settings),
            isSelected = currentRoute == FaceMeDestinations.SETTINGS,
            action = {
                navigateToSettings()
                closeDrawer()
            }
        )
    }
}

@Preview
@Composable
private fun FaceMeLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        FaceMeIcon()
        Spacer(modifier = Modifier.width(8.dp))
    }

}

@Composable
private fun DrawerButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors
    val textIconColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }

    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        Color.Transparent
    }

    val surfaceModifier = modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                NavigationIcon(
                    icon = icon,
                    isSelected = isSelected,
                    contentDescription = null,
                    tintColor = textIconColor
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2,
                    color = textIconColor
                )
            }
        }
    }
}
