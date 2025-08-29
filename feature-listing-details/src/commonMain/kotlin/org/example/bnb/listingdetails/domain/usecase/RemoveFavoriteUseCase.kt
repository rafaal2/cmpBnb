package org.example.bnb.listingdetails.domain.usecase
import org.example.bnb.listingdetails.domain.repository.ListingDetailsRepository

class RemoveFavoriteUseCase(private val repository: ListingDetailsRepository) {
    suspend operator fun invoke(userId: String, listingId: String) =
        repository.removeFavorite(userId, listingId)
}