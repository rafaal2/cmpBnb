package org.example.bnb.discover.di

import org.example.bnb.discover.data.repository.ListingRepositoryImpl
import org.example.bnb.discover.domain.repository.ListingRepository
import org.example.bnb.discover.domain.usecase.GetListingsUseCase
import org.example.bnb.discover.ui.DiscoverViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val discoverModule = module {

    // CORREÇÃO: O ListingRepositoryImpl agora recebe um 'httpClient', não um 'apiService'.
    factory<ListingRepository> { ListingRepositoryImpl(httpClient = get()) }

    // Esta linha continua correta.
    factoryOf(::GetListingsUseCase)

    // Esta linha também está correta, mas a convenção para ViewModels é usar 'viewModelOf' ou 'viewModel'.
    // Vamos ajustá-la para a melhor prática.
    factory { DiscoverViewModel(getListingsUseCase = get()) } //
}