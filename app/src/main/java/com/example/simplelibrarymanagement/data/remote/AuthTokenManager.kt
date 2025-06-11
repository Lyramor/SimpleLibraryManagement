package com.example.simplelibrarymanagement.data.remote

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthTokenManager @Inject constructor() {
    var authToken: String? = null
    // This line MUST be here
    var userId: String? = null

    fun saveToken(token: String, id: String) {
        authToken = token
        userId = id
    }

    fun clearToken() {
        authToken = null
        userId = null
    }
}