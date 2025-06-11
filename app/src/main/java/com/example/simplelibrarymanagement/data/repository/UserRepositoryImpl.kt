package com.example.simplelibrarymanagement.data.repository

import com.example.simplelibrarymanagement.data.remote.ApiService
import com.example.simplelibrarymanagement.domain.model.User
import com.example.simplelibrarymanagement.domain.repository.UserRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
    // Untuk saat ini, implementasi akan menggunakan data dummy.
    // Nanti, Anda tinggal mengganti isi fungsi dengan panggilan ke apiService.
) : UserRepository {

    // Data dummy sementara untuk simulasi
    private val userList = mutableListOf(
        User("admin01", "Alice Johnson", "alice@example.com", "Admin"),
        User("user01", "Bob Williams", "bob@example.com", "User"),
        User("user02", "Charlie Brown", "charlie@example.com", "User")
    )

    override suspend fun getUsers(): List<User> {
        delay(1000) // Simulasi panggil API
        // Nantinya: return apiService.getUsers()
        return userList.toList()
    }

    override suspend fun addUser(user: User) {
        delay(500)
        // Nantinya: apiService.addUser(user)
        userList.add(user)
    }

    override suspend fun updateUser(user: User) {
        delay(500)
        // Nantinya: apiService.updateUser(user.id, user)
        val index = userList.indexOfFirst { it.id == user.id }
        if (index != -1) {
            userList[index] = user
        }
    }

    override suspend fun deleteUser(userId: String) {
        delay(500)
        // Nantinya: apiService.deleteUser(userId)
        userList.removeAll { it.id == userId }
    }
    override suspend fun getUserById(userId: String): User? {
        return try {
            apiService.getUserById(userId)
        } catch (e: Exception) {
            // If the user is not found or another error occurs, return null.
            e.printStackTrace()
            null
        }
    }
}