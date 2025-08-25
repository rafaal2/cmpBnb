package org.example.bnb.favorites.di

import org.example.bnb.favorites.data.repository.FavoritesRepositoryImpl
import org.example.bnb.favorites.domain.repository.FavoritesRepository
import org.example.bnb.favorites.domain.usecase.AddFavoriteUseCase
import org.example.bnb.favorites.domain.usecase.GetFavoritesUseCase
import org.example.bnb.favorites.domain.usecase.RemoveFavoriteUseCase
import org.example.bnb.favorites.ui.FavoritesViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val favoritesModule = module {
    factory<FavoritesRepository> { FavoritesRepositoryImpl(httpClient = get()) }

    factoryOf(::GetFavoritesUseCase)
    factoryOf(::AddFavoriteUseCase)
    factoryOf(::RemoveFavoriteUseCase)

    factory { FavoritesViewModel(
        getFavoritesUseCase = get(),
        removeFavoriteUseCase = get()
    ) }
}