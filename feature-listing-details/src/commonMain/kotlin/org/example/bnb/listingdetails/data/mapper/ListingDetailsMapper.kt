package org.example.bnb.listingdetails.data.mapper

import io.ktor.http.HttpHeaders.Host
import io.ktor.http.HttpHeaders.Location
import org.example.bnb.listingdetails.data.remote.response.*
import org.example.bnb.listingdetails.domain.model.*

/**
 * Funções de extensão para converter DTOs (camada de dados) para Modelos (camada de domínio).
 * Centraliza toda a lógica de "tradução".
 */
fun ListingDetailsDto.toDomainModel(): ListingDetails {
    return ListingDetails(
        id = this.id.toString(),
        name = this.name,
        description = this.description,
        price = this.price,
        rating = this.rating,
        reviewCount = this.reviewCount,
        type = this.type,
        maxGuests = this.maxGuests,
        bedrooms = this.bedrooms,
        beds = this.beds,
        bathrooms = this.bathrooms,
        photos = this.photos,
        host = this.host.toDomainModel(),
        location = this.location.toDomainModel(),
        amenities = this.amenities.map { it.toDomainModel() },
        latestReviews = this.latestReviews.map { it.toDomainModel() }
    )
}

fun HostDto.toDomainModel(): Host {
    return Host(
        id = this.id.toString(),
        name = this.name,
        avatarUrl = this.avatarUrl,
        isSuperhost = this.isSuperhost
    )
}

fun LocationDto.toDomainModel(): Location {
    return Location(
        address = this.address,
        latitude = this.latitude,
        longitude = this.longitude
    )
}

fun AmenityDto.toDomainModel(): Amenity {
    return Amenity(
        name = this.name,
        isAvailable = this.isAvailable
    )
}

fun ReviewDto.toDomainModel(): Review {
    return Review(
        authorName = this.authorName,
        authorAvatarUrl = this.authorAvatarUrl,
        date = this.date,
        comment = this.comment
    )
}