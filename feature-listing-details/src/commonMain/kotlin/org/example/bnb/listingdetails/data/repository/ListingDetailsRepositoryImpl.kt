package org.example.bnb.listingdetails.data.repository

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.bnb.listingdetails.data.mapper.toDomainModel
import org.example.bnb.listingdetails.data.remote.request.ToggleFavoriteRequestDto
import org.example.bnb.listingdetails.data.remote.response.ListingDetailsDto
import org.example.bnb.listingdetails.domain.model.ListingDetails
import org.example.bnb.listingdetails.domain.repository.ListingDetailsRepository

class ListingDetailsRepositoryImpl(
    private val httpClient: HttpClient
) : ListingDetailsRepository {

    private val DETAILS_URL = "http://gestorwebsaude.com.br/vaidehotel/app/api/get_acomodacao_by_id.php"

    private val FAVORITES_API_URL = "http://gestorwebsaude.com.br/vaidehotel/app/api/favoritos.php"

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
    override suspend fun addFavorite(userId: String, listingId: String): Result<Unit> {
        return runCatching {
            Logger.d("ListingDetailsRepo") { "Adicionando favorito: listing=$listingId" }
            val requestDto = ToggleFavoriteRequestDto(
                action = "add",
                userId = userId.toInt(),
                acomodacaoId = listingId.toInt()
            )
            httpClient.post(FAVORITES_API_URL) {
                contentType(ContentType.Application.Json)
                setBody(requestDto)
            }
        }
    }

    // 👇 ADICIONE A IMPLEMENTAÇÃO PARA REMOVER FAVORITO
    override suspend fun removeFavorite(userId: String, listingId: String): Result<Unit> {
        return runCatching {
            Logger.d("ListingDetailsRepo") { "Removendo favorito: listing=$listingId" }
            val requestDto = ToggleFavoriteRequestDto(
                action = "delete",
                userId = userId.toInt(),
                acomodacaoId = listingId.toInt()
            )
            httpClient.post(FAVORITES_API_URL) {
                contentType(ContentType.Application.Json)
                setBody(requestDto)
            }
        }
    }
}