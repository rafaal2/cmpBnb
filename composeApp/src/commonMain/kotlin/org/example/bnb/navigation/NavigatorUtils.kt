package org.example.bnb.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
fun rememberRootNavigator(): Navigator {
    var nav = LocalNavigator.currentOrThrow
    while (nav.parent != null) {
        nav = nav.parent!!
    }
    return nav
}
