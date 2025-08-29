package org.example.bnb.discover.data.repository

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.bnb.discover.data.mapper.toDomainModel
import org.example.bnb.discover.data.remote.response.ListingResponse
import org.example.bnb.discover.domain.model.Listing
import org.example.bnb.discover.domain.repository.ListingRepository

class ListingRepositoryImpl(
    private val httpClient: HttpClient
) : ListingRepository {

    // 👇 MUDANÇA: A URL agora aponta para o seu servidor PHP local
    private val GET_ALL_URL = "http://gestorwebsaude.com.br/vaidehotel/app/api/get_all_acomodacoes.php"

    override suspend fun getListings(): Result<List<Listing>> {
        return runCatching {
            Logger.d("GameRepository") { "Buscando acomodações da API local..." }

            // 👇 A chamada agora usa a nova URL, sem API Key
            val response = httpClient.get(GET_ALL_URL).body<ListingResponse>()

            // A lógica de mapeamento continua a mesma
            response.results.map { it.toDomainModel() }

        }.onFailure { error ->
            Logger.e("GameRepository", error) { "Falha ao buscar acomodações" }
        }
    }
}