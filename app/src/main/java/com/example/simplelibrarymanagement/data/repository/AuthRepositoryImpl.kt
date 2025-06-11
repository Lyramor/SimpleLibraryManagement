package com.example.simplelibrarymanagement.data.repository

import com.example.simplelibrarymanagement.data.model.LoginRequest
import com.example.simplelibrarymanagement.data.model.LoginResponse
import com.example.simplelibrarymanagement.data.model.RegisterRequest
import com.example.simplelibrarymanagement.data.model.RegisterResponse
import com.example.simplelibrarymanagement.data.remote.ApiService
import com.example.simplelibrarymanagement.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        return try {
            val response = apiService.login(loginRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- THIS IS THE MISSING FUNCTION ---
    // This implements the function required by the interface.
    override suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse> {
        return try {
            val response = apiService.register(registerRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}