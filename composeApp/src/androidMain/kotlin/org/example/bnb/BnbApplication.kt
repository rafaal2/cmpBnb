package org.example.bnb

import android.app.Application
import org.example.bnb.core.di.imageLoaderModule
import org.example.bnb.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.example.bnb.discover.di.discoverModule
import org.example.bnb.listingdetails.di.listingDetailsModule
import org.example.bnb.login.di.loginModule
import org.example.bnb.search.di.searchModule
import org.example.coreNetwork.di.coreNetworkModule

class BnbApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BnbApplication)
            modules(
                appModule, // 👈 2. ADICIONE O NOVO MÓDULO À LISTA
                discoverModule,
                loginModule,
                searchModule,
                coreNetworkModule,
                listingDetailsModule,
                imageLoaderModule()
                // coreDatabaseModule
            )
        }
    }
}