plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    compileSdk = 35
    namespace = "com.example.simplelibrarymanagement"

    defaultConfig {
        applicationId = "com.example.simplelibrarymanagement"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "2.0.0"
    }
}

dependencies {
    // Core & Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose - BOM (Bill of Materials) manages versions for Compose libraries
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics) // Corrected alias will be used from libs.versions.toml
    implementation(libs.androidx.ui.tooling.preview) // For @Preview annotations
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.material.icons.extended) // Extended Material Icons for Compose

    // Hilt - Dependency Injection
    implementation(libs.hilt.android)
    implementation(libs.androidx.core.splashscreen)
//    implementation(libs.androidx.runner)
    ksp(libs.hilt.compiler) // Hilt's KSP compiler
    implementation(libs.hilt.navigation.compose) // Hilt integration with Jetpack Navigation Compose

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Room - Local Database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx) // Kotlin Extensions for Room
    ksp(libs.room.compiler) // Room's KSP compiler

    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json)

    // DataStore - Preferences
    implementation(libs.datastore.preferences)

    // Image Loading - Coil
    implementation(libs.coil.compose)

    // Networking - Retrofit & OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson) // Gson converter for Retrofit
    implementation(libs.okhttp) // OkHttp client
    implementation(libs.okhttp.logging.interceptor) // OkHttp logging interceptor

    // Add image picker and upload functionality
    implementation(libs.androidx.activity.compose.v182)

    // Unit Tests (run on local JVM)
    testImplementation(libs.junit) // Standard JUnit4
    testImplementation(libs.kotlinx.coroutines.test) // For testing coroutines
    testImplementation(libs.truth) // Google's Truth assertion library
    testImplementation(libs.mockito.core) // Mockito for mocking
    testImplementation(libs.mockito.kotlin) // Mockito-Kotlin integration
    testImplementation(libs.arch.core.testing) // For testing Architecture Components (LiveData, etc.)
    // testImplementation(libs.robolectric) // If using Robolectric

    // Android Instrumented Tests (run on an Android device or emulator)
    androidTestImplementation(libs.androidx.junit) // androidx.test.ext:junit (uses junitVersion from libs.versions.toml)
    // androidTestImplementation(libs.androidx.junit.ktx) // If you have androidx-junit-ktx alias
    androidTestImplementation(libs.androidx.espresso.core) // Espresso for UI testing (uses espressoCore from libs.versions.toml)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // Align Compose test versions with BOM
    androidTestImplementation(libs.androidx.ui.test.junit4) // Base Compose UI testing
    androidTestImplementation(libs.androidx.ui.test.junit4.android) // Android specific Compose UI testing (uses uiTestJunit4Android)
    androidTestImplementation(libs.kotlinx.coroutines.test) // For testing coroutines in instrumented tests
    androidTestImplementation(libs.androidx.runner) // AndroidX Test Runner


    androidTestImplementation(libs.core.ktx)
    // Hilt Testing (for instrumented tests)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler) // Hilt KSP compiler for AndroidTest

    // Debugging - Only included in debug builds
    debugImplementation(libs.androidx.ui.tooling) // Compose UI tooling for Layout Inspector, etc.
    debugImplementation(libs.androidx.ui.test.manifest) // Compose test manifest
    debugImplementation(libs.chucker) // Chucker for HTTP inspection
    // releaseImplementation(libs.chucker.no.op) // No-op version of Chucker for release builds
}