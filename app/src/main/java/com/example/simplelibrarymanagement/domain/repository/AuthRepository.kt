package com.example.simplelibrarymanagement.domain.repository

import com.example.simplelibrarymanagement.data.model.LoginRequest
import com.example.simplelibrarymanagement.data.model.LoginResponse
// --- THIS IS THE FIX ---
import com.example.simplelibrarymanagement.data.model.RegisterRequest
import com.example.simplelibrarymanagement.data.model.RegisterResponse

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse>

    suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse>
}