package com.example.gessport.ui.backend.ges_user
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gessport.repository.UserRepository
import com.example.gessport.models.User
import kotlinx.coroutines.launch
class GesUserViewModel {



    class GesUserViewModel (val userRepository: UserRepository): ViewModel() {
        private var _users by mutableStateOf<List<User>>(emptyList())
        val users: List<User> get() = _users

        private var _selectedRole by mutableStateOf<String?>(null)
        val selectedRole: String? get() = _selectedRole

        init {
            //podemos utilizar directamente loadUsers()
            viewModelScope.launch {
                _users = userRepository.getAllUsers()
            }
        }

        fun loadUsers(){
            viewModelScope.launch {
                if(_selectedRole==null){
                    _users=userRepository.getAllUsers()
                }else{
                    _users=userRepository.getUsersByRole(_selectedRole!!)
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
        fun addUser(user: User){
            viewModelScope.launch{
                userRepository.addUser(user)
                //recargar la lista
            }
        }
    }
}