package org.example.bnb.game.di

import org.example.bnb.game.data.repository.GameRepositoryImpl
import org.example.bnb.game.domain.repository.GameRepository
import org.example.bnb.game.domain.usecase.GetGamesUseCase
import org.example.bnb.game.ui.GameViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val gameModule = module {

    // CORREÇÃO: O GameRepositoryImpl agora recebe um 'httpClient', não um 'apiService'.
    factory<GameRepository> { GameRepositoryImpl(httpClient = get()) }

    // Esta linha continua correta.
    factoryOf(::GetGamesUseCase)

    // Esta linha também está correta, mas a convenção para ViewModels é usar 'viewModelOf' ou 'viewModel'.
    // Vamos ajustá-la para a melhor prática.
    factory { GameViewModel(getGamesUseCase = get()) } //
}