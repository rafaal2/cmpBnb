package org.example.bnb.listingdetails.data.repository

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.example.bnb.listingdetails.data.mapper.toDomainModel
import org.example.bnb.listingdetails.data.remote.response.ListingDetailsDto
import org.example.bnb.listingdetails.domain.model.ListingDetails
import org.example.bnb.listingdetails.domain.repository.ListingDetailsRepository

class ListingDetailsRepositoryImpl(
    private val httpClient: HttpClient
) : ListingDetailsRepository {

    private val DETAILS_URL = "http://10.0.2.2/testeSession/api/get_acomodacao_by_id.php"

    override suspend fun getListingDetails(id: String): Result<ListingDetails> {
        return runCatching {
            Logger.d("ListingDetailsRepo") { "Buscando detalhes para o ID: $id" }

            val responseDto = httpClient.get(DETAILS_URL) {
                parameter("id", id)
            }.body<ListingDetailsDto>()

            responseDto.toDomainModel()

        }.onFailure { error ->
            Logger.e("ListingDetailsRepo", error) { "Falha ao buscar detalhes para o ID: $id" }
        }
    }
}