
// Pastikan versi plugin ini sudah terbaru atau yang direkomendasikan
plugins {
    alias(libs.plugins.android.application) apply false // Contoh versi terbaru: 8.4.0
    alias(libs.plugins.kotlin.android) apply false     // Contoh versi terbaru: 2.0.0
    alias(libs.plugins.kotlin.compose) apply false     // Versi ini sering mengikuti kotlin.android
}