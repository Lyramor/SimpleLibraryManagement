package com.example.simplelibrarymanagement

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
@HiltAndroidApp
class MyLibraryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Anda bisa menambahkan kode inisialisasi lain di sini jika diperlukan.
        // Misalnya, inisialisasi library pihak ketiga, logging, dll.
        // Untuk Hilt, anotasi @HiltAndroidApp sudah cukup untuk setup dasar.
    }
}
