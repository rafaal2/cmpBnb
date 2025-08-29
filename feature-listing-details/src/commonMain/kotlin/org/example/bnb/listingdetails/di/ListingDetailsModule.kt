package org.example.bnb.listingdetails.di

import org.example.bnb.listingdetails.data.repository.ListingDetailsRepositoryImpl
import org.example.bnb.listingdetails.domain.repository.ListingDetailsRepository
import org.example.bnb.listingdetails.domain.usecase.AddFavoriteUseCase
import org.example.bnb.listingdetails.domain.usecase.GetListingDetailsUseCase
import org.example.bnb.listingdetails.domain.usecase.RemoveFavoriteUseCase
import org.example.bnb.listingdetails.ui.ListingDetailsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val listingDetailsModule = module {
    // Repositório
    factory<ListingDetailsRepository> { ListingDetailsRepositoryImpl(httpClient = get()) }

    // UseCases
    factoryOf(::GetListingDetailsUseCase)
    factoryOf(::AddFavoriteUseCase)
    factoryOf(::RemoveFavoriteUseCase)

    // ViewModel que recebe o ID como parâmetro
    factory { params ->
        ListingDetailsViewModel(
            listingId = params.get(),
            getListingDetailsUseCase = get(),
            addFavoriteUseCase = get(),
            removeFavoriteUseCase = get()
        )
    }
}