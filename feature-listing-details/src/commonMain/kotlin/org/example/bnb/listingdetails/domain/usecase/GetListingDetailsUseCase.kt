package org.example.bnb.listingdetails.domain.usecase

import org.example.bnb.listingdetails.domain.repository.ListingDetailsRepository

/**
 * O caso de uso para buscar os detalhes de uma acomodação.
 * Ele simplesmente orquestra a chamada para o repositório.
 * No futuro, poderia conter regras de negócio adicionais (ex: verificar cache antes de chamar a API).
 */
class GetListingDetailsUseCase(
    private val repository: ListingDetailsRepository
) {
    suspend operator fun invoke(id: String) = repository.getListingDetails(id)
}