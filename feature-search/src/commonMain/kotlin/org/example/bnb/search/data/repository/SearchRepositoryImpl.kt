package org.example.bnb.search.data.repository

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.example.bnb.search.data.mapper.toDomainModel
import org.example.bnb.search.data.remote.response.SearchResponse
import org.example.bnb.search.domain.model.SearchResult
import org.example.bnb.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val httpClient: HttpClient
) : SearchRepository {

    private val SEARCH_URL = "http://gestorwebsaude.com.br/vaidehotel/app/api/get_acomodacoes_by_name.php"

    override suspend fun search(query: String): Result<List<SearchResult>> {
        return runCatching {
            Logger.d("SearchRepository") { "Buscando por: $query" }

            val response = httpClient.get(SEARCH_URL) {
                parameter("name", query)
            }.body<SearchResponse>()

            response.results.map { it.toDomainModel() }

        }.onFailure { error ->
            Logger.e("SearchRepository", error) { "Falha na busca" }
        }
    }
}