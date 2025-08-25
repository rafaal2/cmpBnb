package org.example.bnb.favorites.data.repository

import co.touchlab.kermit.Logger
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.example.bnb.favorites.data.mapper.toDomainModel
import org.example.bnb.favorites.data.remote.request.ToggleFavoriteRequestDto
import org.example.bnb.favorites.data.remote.response.FavoritesResponseDto
import org.example.bnb.favorites.domain.model.FavoriteListing
import org.example.bnb.favorites.domain.repository.FavoritesRepository

class FavoritesRepositoryImpl(
    private val httpClient: HttpClient
) : FavoritesRepository {

    private val FAVORITES_API_URL = "http://10.0.2.2/testeSession/api/favoritos.php"

    override suspend fun getFavorites(userId: String): Result<List<FavoriteListing>> {
        return runCatching {
            Logger.d("FavoritesRepository") { "Buscando favoritos para o usuário: $userId" }

            val response = httpClient.get(FAVORITES_API_URL) {
                parameter("user_id", userId)
            }.body<FavoritesResponseDto>()

            response.results.map { it.toDomainModel() }
        }
    }

    override suspend fun addFavorite(userId: String, listingId: String): Result<Unit> {
        return runCatching {
            Logger.d("FavoritesRepository") { "Adicionando favorito: listing=$listingId para user=$userId" }

            val requestDto = ToggleFavoriteRequestDto(
                action = "add",
                userId = userId.toInt(), // Cuidado com a conversão de String para Int
                acomodacaoId = listingId.toInt()
            )

            httpClient.post(FAVORITES_API_URL) {
                contentType(ContentType.Application.Json)
                setBody(requestDto)
            }
        }
    }

    override suspend fun removeFavorite(userId: String, listingId: String): Result<Unit> {
        return runCatching {
            Logger.d("FavoritesRepository") { "Removendo favorito: listing=$listingId do user=$userId" }

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