plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    // Define os alvos que esta biblioteca precisa suportar,
    // pois todos os outros módulos dependerão dela.
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            // A única dependência necessária é o núcleo da biblioteca de navegação,
            // para que possamos usar tipos como 'Screen'.
            implementation(libs.voyager.navigator)
        }
        // Os sourceSets de plataforma geralmente ficam vazios em um módulo de contrato como este.
        androidMain.dependencies { }
        iosMain.dependencies { }
    }
}

// Configuração mínima para que o módulo seja uma biblioteca Android válida
android {
    namespace = "org.example.bnb.core.navigation"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}