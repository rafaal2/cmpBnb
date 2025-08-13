package org.example.bnb.di

import com.russhwolf.settings.Settings
import org.example.bnb.splash.SplashViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
    factory { Settings() }

    factoryOf(::SplashViewModel)
}