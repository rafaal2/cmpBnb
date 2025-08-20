package org.example.bnb

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

/**
 * Define a MainScreen como um objeto 'Screen' da Voyager.
 * Este é o ponto de entrada para toda a seção principal do app (pós-login).
 */
object MainScreen : Screen {
    @Composable
    override fun Content() {
        // Chama o Composable que contém a UI real.
        MainScreenContent()
    }
}

/**
 * Contém a UI real da tela principal, com o Scaffold, TopAppBar,
 * BottomNavigationBar e o navegador de abas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent() {
    val rootNavigator = LocalNavigator.currentOrThrow
    val appNavigator  = androidx.compose.runtime.remember(rootNavigator) { AppNavigator(rootNavigator) }

    // O TabNavigator gerencia o estado das abas (qual está selecionada).
    TabNavigator(DiscoverTab) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        // Barra de busca "falsa" que serve como um botão
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 16.dp)
                                .height(48.dp)
                                .clickable { appNavigator.open(AppRoute.Search) }
                                .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Ícone de busca",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Pesquisa",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    // A Voyager exibe o conteúdo da aba atual aqui
                    CurrentTab()
                }
            },
            bottomBar = {
                NavigationBar {
                    // Os itens da barra de navegação
                    TabNavigationItem(tab = DiscoverTab)
                    TabNavigationItem(tab = FavoritesTab)
                    TabNavigationItem(tab = ReserveTab)
                    TabNavigationItem(tab = ProfileTab)
                }
            }
        )
    }
}

/**
 * Um Composable helper para criar os itens da Bottom Navigation Bar, evitando repetição.
 */
@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current.options.index == tab.options.index,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) },
        label = { Text(tab.options.title) }
    )
}