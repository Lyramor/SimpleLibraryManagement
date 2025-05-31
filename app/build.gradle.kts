// app/build.gradle.kts (Modul Aplikasi)
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    // Pastikan compileSdk ini juga didukung oleh versi AGP yang kamu pakai
    compileSdk = 35 // Contoh, bisa lebih tinggi jika ada update SDK baru

    namespace = "com.example.simplelibrarymanagement"

    defaultConfig {
        applicationId = "com.example.simplelibrarymanagement"
        minSdk = 24
        targetSdk = 35 // Contoh, sesuaikan dengan compileSdk
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
        // Pastikan versi kotlinCompilerExtensionVersion cocok dengan versi Kotlin dan AGP
        kotlinCompilerExtensionVersion = "2.0.0" // Contoh, cocok dengan Kotlin 2.0.0
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose BOM (Bill of Materials) - selalu gunakan platform() untuk BOM
    implementation(platform(libs.androidx.compose.bom))

    // Compose UI
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Extended icons (untuk ikon seperti visibility)
    implementation("androidx.compose.material:material-icons-extended")

    // Lifecycle Compose
    implementation(libs.androidx.lifecycle.runtime.compose.android)

    // Splash Screen
    implementation(libs.androidx.core.splashscreen)

    // Unit Testing
    testImplementation(libs.junit)

    // Android Instrumentation Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debugging tools
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}