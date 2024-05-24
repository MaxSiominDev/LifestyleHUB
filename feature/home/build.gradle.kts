plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.roomCompiler)
    kotlin("kapt")
}

android {
    namespace = "dev.maxsiomin.prodhse.feature.home"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "dev.maxsiomin.prodhse.testing.HiltTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.runner)
    testImplementation(project(":core"))
    debugImplementation(libs.ui.tooling)

    // Testing
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(project(":testing"))
    androidTestImplementation(project(":testing"))
    testImplementation(libs.mockk)
    testImplementation(libs.junit)
    testImplementation(libs.google.truth)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(project(":navdestinations"))

    // Network requests with Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.logging)

    implementation(project(":core"))
    implementation(project(":common"))

    // Hilt for DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Logging with Timber
    implementation(libs.timber)

    // Image loading lib
    implementation(libs.coil.compose)

    // Shared prefs to save auth info
    implementation(libs.androidx.preference.ktx)

    // Icons
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)

    // Room DB to save plans
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.room.compiler)
    implementation(libs.androidx.room.ktx)

}
