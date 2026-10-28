import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    // Necesario para que las rutas (@Serializable) funcionen con Navigation 3
    alias(libs.plugins.kotlin.serialization)
}

// El token público se toma del equipo local o del entorno de CI; nunca del repositorio del profesor.
val propiedadesLocales = Properties().apply {
    rootProject.file("local.properties").takeIf { it.isFile }?.inputStream()?.use { load(it) }
}
val tokenMapbox = providers.environmentVariable("MAPBOX_ACCESS_TOKEN")
    .orElse(providers.provider { propiedadesLocales.getProperty("MAPBOX_ACCESS_TOKEN", "") })
    .get().trim()
require(tokenMapbox.isEmpty() || Regex("pk\\.[A-Za-z0-9._-]+").matches(tokenMapbox)) {
    "MAPBOX_ACCESS_TOKEN debe ser un token público de Mapbox (pk.). No se admiten tokens privados."
}

android {
    namespace = "com.javeriana.javewheels"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.javeriana.javewheels"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
        resValue("string", "mapbox_access_token", tokenMapbox)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        resValues = true
    }
}

dependencies {
    // Solo renderizado de mapas con Compose y consultas puntuales de ubicación.
    implementation(libs.mapbox.maps)
    implementation(libs.mapbox.compose)
    implementation(libs.play.services.location)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material3)

    // Navigation 3 y ViewModel
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.kotlinx.serialization.json)

    // Íconos de Material
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
