package com.example.gessport.repository

import com.example.gessport.models.User

interface UserRepository {
    suspend fun updateUser(user: User): Boolean
    suspend fun getAllUsers(): List<User>
    suspend fun getUsersByRole(rol: String): List<User>
    suspend fun getUserById(id: Int): User?
    suspend fun addUser(user: User): User
    suspend fun deleteUser(id: Int): Boolean
}