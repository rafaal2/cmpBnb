import org.gradle.declarative.dsl.schema.FqName.Empty.packageName

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sql.delight) // Plugin do SQLDelight
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            // Dependências do SQLDelight
            implementation(libs.sql.delight.common)
            implementation(libs.sql.delight.common.coroutines)
        }
        androidMain.dependencies {
            implementation(libs.sql.delight.android)
        }
        iosMain.dependencies {
            implementation(libs.sql.delight.ios)
        }
    }
}

android {
    namespace = "org.example.bnb.core.database"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

// Configuração para o SQLDelight gerar o código a partir dos arquivos .sq
//sqldelight {
//    database("AppDatabase") { // O nome que você dará para a classe do seu banco de dados
//        packageName = "com.example.db"
//    }
//}