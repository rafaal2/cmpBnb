package org.example.bnb.login.di

import org.example.bnb.login.data.repository.LoginRepositoryImpl
import org.example.bnb.login.domain.repository.LoginRepository
import org.example.bnb.login.domain.usecase.DoLoginUseCase
import org.example.bnb.login.ui.LoginViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

/**
 * Módulo de injeção de dependência do Koin para a feature de login. (Versão simplificada)
 */
val loginModule = module {

    // Provê a implementação para a interface do repositório.
    factory<LoginRepository> { LoginRepositoryImpl(httpClient = get()) }

    // Provê o UseCase principal.
    factoryOf(::DoLoginUseCase)

    // Define como criar o LoginViewModel.
    // Agora ele só precisa do DoLoginUseCase.
    factory { LoginViewModel(doLoginUseCase = get()) }
}