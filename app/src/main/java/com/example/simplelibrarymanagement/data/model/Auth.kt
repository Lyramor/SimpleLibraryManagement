package com.example.simplelibrarymanagement.data.model

import com.google.gson.annotations.SerializedName

// This defines the JSON object we will SEND to the server.
// e.g., {"username": "test", "password": "123"}
// In Auth.kt, find and replace LoginRequest with this:
data class LoginRequest(
    val email: String, // Changed from username
    val password: String
)

// This defines the JSON object we will RECEIVE from the server.
// e.g., {"access_token": "some_long_jwt_string", "message": "Login successful"}
// In Auth.kt
data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("user_id") // Add this
    val userId: String,      // Add this
    val role: String,
    val message: String
)

// Add these new data classes to the Auth.kt file

// This defines the JSON for the registration request.
data class RegisterRequest(
    val name: String, // Changed from username
    val email: String,
    val password: String,
    @SerializedName("role_id") // Ensures the JSON key is "role_id"
    val roleId: Int
)

// This defines the JSON we expect back from the server after registration.
data class RegisterResponse(
    val message: String
)