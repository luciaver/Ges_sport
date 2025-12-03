package com.example.gessport.data

import com.example.gessport.models.User
import com.example.gessport.repository.UserRepository

class ApiUserRepository {


    class ApiUserRepository: UserRepository {
        override suspend fun getAllUsers(): List<User> {
            TODO("Not yet implemented")
        }

        override suspend fun getUsersByRole(rol: String): List<User> {
            TODO("Not yet implemented")
        }

        override suspend fun getUserById(id: Int): User? {
            TODO("Not yet implemented")
        }

        override suspend fun addUser(user: User): User {
            TODO("Not yet implemented")
        }

        override suspend fun updateUser(user: User): Boolean {
            TODO("Not yet implemented")
        }

        override suspend fun deleteUser(id: Int): Boolean {
            TODO("Not yet implemented")
        }
    }
}