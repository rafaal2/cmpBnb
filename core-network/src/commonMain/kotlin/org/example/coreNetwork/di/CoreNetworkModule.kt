package org.example.coreNetwork.di

import io.ktor.client.HttpClient
import org.example.coreNetwork.apiService.ApiService
import org.example.coreNetwork.client.KtorClient
import org.koin.dsl.module

fun getCoreNetoworkModule() = module {
    single { ApiService(httpClient = KtorClient.getInstance()) }
}