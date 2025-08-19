package org.example.bnb.listingdetails.di

import org.example.bnb.listingdetails.data.repository.ListingDetailsRepositoryImpl
import org.example.bnb.listingdetails.domain.repository.ListingDetailsRepository
import org.example.bnb.listingdetails.domain.usecase.GetListingDetailsUseCase
import org.example.bnb.listingdetails.ui.ListingDetailsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val listingDetailsModule = module {
    factory<ListingDetailsRepository> { ListingDetailsRepositoryImpl(httpClient = get()) }
    factoryOf(::GetListingDetailsUseCase)

    // O ViewModel agora recebe um parâmetro (o ID)
    factory { params ->
        ListingDetailsViewModel(
            listingId = params.get(), // Koin pega o primeiro parâmetro passado
            getListingDetailsUseCase = get()
        )
    }
}