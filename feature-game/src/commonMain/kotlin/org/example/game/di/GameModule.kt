package org.example.game.di

import org.example.game.data.repository.GameRepositoryImpl
import org.example.game.domain.repository.GameRepository
import org.example.game.domain.usecase.GetGamesUseCase
import org.example.game.ui.GameViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

// Convenção: val com letra minúscula
val gameModule = module {
    // Quando alguém pedir um GameRepository (interface), entregue um GameRepositoryImpl
    factory<GameRepository> { GameRepositoryImpl(apiService = get()) }

    // Koin sabe criar um GetGamesUseCase porque já sabe criar seu dependente (GameRepository)
    factoryOf(::GetGamesUseCase)

    // Definição do ViewModel
    factory { GameViewModel(getGamesUseCase = get()) }
}