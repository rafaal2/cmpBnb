package org.example.bnb.listingdetails.domain.model

/**
 * Representa todas as informações de uma acomodação que a UI precisa para
 * desenhar a tela de detalhes. Este é um modelo de negócio puro.
 */
data class ListingDetails(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val reviewCount: Int,
    val type: String,
    val maxGuests: Int,
    val bedrooms: Int,
    val beds: Int,
    val bathrooms: Int,
    val photos: List<String>,
    val host: Host,
    val location: Location,
    val amenities: List<Amenity>,
    val latestReviews: List<Review>
)

data class Host(
    val id: String,
    val name: String,
    val avatarUrl: String,
    val isSuperhost: Boolean
)

data class Location(
    val address: String,
    val latitude: Double,
    val longitude: Double
)

data class Amenity(
    val name: String,
    val isAvailable: Boolean
)

data class Review(
    val authorName: String,
    val authorAvatarUrl: String,
    val date: String,
    val comment: String
)