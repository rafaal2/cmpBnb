package org.example.bnb

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.example.bnb.core.navigation.AppRoute
import org.example.bnb.navigation.AppNavigator
import org.example.bnb.navigation.DiscoverTab
import org.example.bnb.navigation.FavoritesTab
import org.example.bnb.navigation.ProfileTab
import org.example.bnb.navigation.ReserveTab
import org.example.bnb.ui.theme.primaryGradientBrush

object MainScreen : Screen {
    @Composable
    override fun Content() {
        MainScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent() {
    val rootNavigator = LocalNavigator.currentOrThrow
    val appNavigator  = remember(rootNavigator) { AppNavigator(rootNavigator) }

    TabNavigator(DiscoverTab) { tabNavigator ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 16.dp, bottom = 10.dp)
                                .height(48.dp)
                                .clickable { appNavigator.open(AppRoute.Search) }
                                .border(1.dp, MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f), CircleShape) // Borda mais clara
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Ícone de busca",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = "Para onde vamos?",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.background(brush = primaryGradientBrush())
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    CurrentTab()
                }
            },
            bottomBar = {
                NavigationBar(
                    modifier = Modifier.background(brush = primaryGradientBrush()),
                    containerColor = Color.Transparent
                ) {
                    TabNavigationItem(tab = DiscoverTab)
                    TabNavigationItem(tab = FavoritesTab)
                    TabNavigationItem(tab = ReserveTab)
                    TabNavigationItem(tab = ProfileTab)
                }
            }
        )
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current.options.index == tab.options.index,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) },
        label = { Text(tab.options.title) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
            unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
        )

    )
}