package org.example.coreNetwork.di

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val coreNetworkModule = module {
    // A ÚNICA responsabilidade é fornecer um cliente genérico com plugins.
    // NENHUMA informação sobre URLs ou endpoints aqui.
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }
}