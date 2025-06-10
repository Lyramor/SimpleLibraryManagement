package com.example.simplelibrarymanagement.domain.repository

import com.example.simplelibrarymanagement.domain.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun addUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(userId: String)
}
