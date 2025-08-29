package org.example.bnb.core.navigation

sealed class AppRoute {
    data class ListingDetails(val listingId: String) : AppRoute()
    data object Search : AppRoute()
    data object Favorites : AppRoute()
}