package org.example.bnb.listingdetails.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTOs que espelham 1-para-1 a resposta JSON da API de detalhes.
 * A anotação @Serializable é essencial para o Ktor/kotlinx.serialization.
 */
@Serializable
data class ListingDetailsDto(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val rating: Double,
    @SerialName("reviewCount") // No PHP o 'C' é maiúsculo, o @SerialName corrige isso
    val reviewCount: Int,
    val type: String,
    @SerialName("maxGuests")
    val maxGuests: Int,
    val bedrooms: Int,
    val beds: Int,
    val bathrooms: Int,
    val photos: List<String>,
    val host: HostDto,
    val location: LocationDto,
    val amenities: List<AmenityDto>,
    @SerialName("latestReviews")
    val latestReviews: List<ReviewDto>
)

@Serializable
data class HostDto(
    val id: Int,
    val name: String,
    @SerialName("avatarUrl")
    val avatarUrl: String,
    @SerialName("isSuperhost")
    val isSuperhost: Boolean
)

@Serializable
data class LocationDto(
    val address: String,
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class AmenityDto(
    val name: String,
    @SerialName("available") // 'available' é uma palavra reservada em alguns contextos, @SerialName garante
    val isAvailable: Boolean
)

@Serializable
data class ReviewDto(
    @SerialName("authorName")
    val authorName: String,
    @SerialName("authorAvatarUrl")
    val authorAvatarUrl: String,
    val date: String,
    val comment: String
)