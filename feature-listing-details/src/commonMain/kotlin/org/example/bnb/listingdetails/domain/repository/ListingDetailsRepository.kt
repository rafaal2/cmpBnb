package org.example.bnb.listingdetails.domain.repository

import org.example.bnb.listingdetails.domain.model.ListingDetails

/**
 * A interface (contrato) que define as operações de dados para a tela de detalhes.
 * O UseCase dependerá desta abstração.
 */
interface ListingDetailsRepository {
    suspend fun getListingDetails(id: String): Result<ListingDetails>
}