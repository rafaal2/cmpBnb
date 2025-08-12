package org.example.bnb.core.di

import coil3.ImageLoader
import coil3.network.ktor3.KtorNetworkFetcherFactory
import io.ktor.client.HttpClient
import org.koin.dsl.module

actual fun imageLoaderModule() = module {
    single {
        ImageLoader.Builder(get())
            .components {
                // 👇 CORREÇÃO: Especifique o tipo para o 'get()'
                add(KtorNetworkFetcherFactory(httpClient = get<HttpClient>()))
            }
            .build()
    }
}