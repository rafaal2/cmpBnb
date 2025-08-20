package org.example.bnb.discover.domain.usecase

import org.example.bnb.discover.domain.repository.ListingRepository

class GetListingsUseCase(
    private val repository: ListingRepository
) {
    // O ViewModel chamará este UseCase, não o repositório diretamente.
    suspend operator fun invoke() = repository.getListings()
}