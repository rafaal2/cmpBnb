plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary) // É uma biblioteca Android, não um app
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler) // Necessário para o @Composable
}

kotlin {
    // Define os alvos que esta biblioteca suporta
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm("desktop") // Habilita o alvo para Desktop/JVM

    sourceSets {
        commonMain.dependencies {
            // Dependências de UI do Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)

            // Dependências de Koin para DI
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.coil)
            implementation(libs.coil.ktor)

            // Dependências dos módulos core que a feature precisa
            implementation(projects.coreNetwork)
            implementation(projects.coreDatabase) // Adicione esta se o game usar o DB
        }

        // Dependências específicas de cada plataforma, se houver
        androidMain.dependencies {
            // Nenhuma específica por enquanto
        }
        iosMain.dependencies {
            // Nenhuma específica por enquanto
        }
    }
}

// Configuração mínima para que o módulo seja uma biblioteca Android válida
android {
    namespace = "org.example.bnb.game"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Pode usar 1.8 para bibliotecas
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}