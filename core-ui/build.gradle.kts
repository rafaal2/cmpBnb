plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    // Define os alvos que esta biblioteca de UI precisa suportar
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            // Dependências essenciais do Compose para criar a UI
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3) // Para MaterialTheme, ColorScheme, etc.
            implementation(compose.ui)
            implementation(compose.components.resources) // Para carregar fontes, imagens, etc.

            // Dependência da biblioteca de ícones, se você for usar ícones nos seus componentes
            implementation(compose.materialIconsExtended)
        }

        androidMain.dependencies {
            // Dependências específicas do Android, como a para o preview
            implementation(compose.preview)
            implementation(libs.androidx.core.ktx) // Necessária para o expect/actual de SystemAppearance
        }

        iosMain.dependencies {
            // Geralmente vazio para um módulo de UI
        }
    }
}

// Configuração mínima para que o módulo seja uma biblioteca Android válida
android {
    namespace = "org.example.bnb.core.ui"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}