package org.example.bnb.core.navigation

// O "cardápio" de destinos que as features podem solicitar.
sealed class AppRoute {
    data class ListingDetails(val listingId: String) : AppRoute()
    object Search : AppRoute()
}