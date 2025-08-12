package org.example.bnb

import android.app.Application
import org.example.bnb.core.di.imageLoaderModule
import org.koin.core.context.startKoin
import org.example.game.di.gameModule
import org.example.coreNetwork.di.getCoreNetoworkModule// Importe o módulo da sua feature
import org.koin.android.ext.koin.androidContext

// Importe também os módulos core se eles tiverem injeções (ex: Ktor, SQLDelight)
// import org.example.core.network.di.coreNetworkModule
// import org.example.core.database.di.coreDatabaseModule


class BnbApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Informa ao Koin sobre o contexto do Android, necessário para algumas libs
            androidContext(this@BnbApplication)

            // Carrega todos os seus módulos de dependência
            modules(
                gameModule,
                getCoreNetoworkModule(), // Descomente quando tiver
                // coreDatabaseModule // Descomente quando tiver
                imageLoaderModule()
            )
        }
    }
}