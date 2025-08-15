// build.gradle.kts do módulo :core-network

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinx.serialization) // Necessário para o Ktor processar JSON
}

kotlin {
    // Define os alvos que esta biblioteca suporta
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            // Dependências essenciais para a rede
            implementation(libs.kotlinx.serialization)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging) // Útil para debugging

            // Dependência do Koin para poder definir o 'coreNetworkModule'
            implementation(libs.koin.core)

            implementation(libs.multiplatform.settings.noarg)
        }

        // Dependências de "motores" (engines) específicos de cada plataforma
        androidMain.dependencies {
            implementation(libs.ktor.client.android) // Engine OkHttp para Android
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.ios) // Engine Darwin para iOS
        }
//        desktopMain.dependencies { // O source set para jvm("desktop")
//            implementation(libs.ktor.client.desktop) // Engine CIO para Desktop
//        }
    }
}

// Configuração mínima para que o módulo seja uma biblioteca Android válida
android {
    namespace = "org.example.bnb.core.network" // Namespace corrigido
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}