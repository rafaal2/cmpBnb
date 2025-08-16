package org.example.bnb

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.example.bnb.navigation.FavoritesTab
import org.example.bnb.navigation.GameTab
import org.example.bnb.navigation.ProfileTab
import org.example.bnb.navigation.ReserveTab

@Composable
fun MainScreen() {
    // 1. Inicia o TabNavigator, definindo a GameTab como a aba inicial.
    TabNavigator(GameTab) { tabNavigator ->
        Scaffold(
            content = { paddingValues ->
                // 2. A Voyager exibe o conteúdo da aba atual aqui.
                Box(modifier = Modifier.padding(paddingValues)) {
                    CurrentTab()
                }
            },
            bottomBar = {
                NavigationBar {
                    val tabs = listOf(GameTab, FavoritesTab, ReserveTab,ProfileTab)

                    tabs.forEach { tab ->
                        NavigationBarItem(
                            selected = tabNavigator.current == tab,
                            onClick = { tabNavigator.current = tab },
                            icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) },
                            label = { Text(tab.options.title) }
                        )
                    }
                }
            }
        )
    }
}