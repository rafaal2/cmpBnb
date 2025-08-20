package org.example.bnb.navigation

import cafe.adriel.voyager.navigator.Navigator
import org.example.bnb.core.navigation.AppRoute
import org.example.bnb.listingdetails.ui.ListingDetailsScreen
import org.example.bnb.search.ui.SearchScreen

class AppNavigator(private val root: Navigator) {

    fun open(route: AppRoute) {
        when (route) {
            is AppRoute.ListingDetails -> root.push(ListingDetailsScreen(route.listingId))
            AppRoute.Search            -> {
                // Abre a Search passando o MESMO adaptador, para que Search
                // consiga emitir AppRoute.ListingDetails
                root.push(SearchScreen(onNavigate = ::open))
            }
        }
    }
}
