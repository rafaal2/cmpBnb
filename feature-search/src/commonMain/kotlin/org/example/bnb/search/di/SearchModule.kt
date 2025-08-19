package org.example.bnb.search.di

import org.example.bnb.search.data.repository.SearchRepositoryImpl
import org.example.bnb.search.domain.repository.SearchRepository
import org.example.bnb.search.domain.usecase.PerformSearchUseCase
import org.example.bnb.search.ui.SearchViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val searchModule = module {
    factory<SearchRepository> { SearchRepositoryImpl(httpClient = get()) }
    factoryOf(::PerformSearchUseCase)
    factory { SearchViewModel(performSearchUseCase = get()) }
}