package com.example.gessport.ui.backend.ges_user

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gessport.repository.UserRepository
import com.example.gessport.models.User
import kotlinx.coroutines.launch

class GesUserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var _users by mutableStateOf<List<User>>(emptyList())
    val users: List<User> get() = _users

    private var _selectedRole by mutableStateOf<String?>(null)
    val selectedRole: String? get() = _selectedRole

    private var _userToEdit by mutableStateOf<User?>(null)
    val userToEdit: User? get() = _userToEdit

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _users = if (_selectedRole == null) {
                userRepository.getAllUsers()
            } else {
                userRepository.getUsersByRole(_selectedRole!!)
            }
        }
    }

    fun onRoleSelected(rol: String?) {
        _selectedRole = rol
        viewModelScope.launch {
            _users = if (rol == null) {
                userRepository.getAllUsers()
            } else {
                userRepository.getUsersByRole(rol)
            }
        }
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.addUser(user)
            loadUsers()
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            userRepository.updateUser(user)
            loadUsers()
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            userRepository.deleteUser(id)
            loadUsers()
        }
    }

    fun getUserById(id: Int) {
        viewModelScope.launch {
            _userToEdit = userRepository.getUserById(id)
        }
    }
}