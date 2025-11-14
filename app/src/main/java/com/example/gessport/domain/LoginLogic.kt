package com.example.gessport.domain

import com.example.gessport.models.User
import com.example.gessport.data.RepositorioDeInicioDeSesion

class LogicLogin {

    fun comprobarLogin(email: String, password: String): User {
        if (email.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("Los campos no pueden estar vacíos.")
        }

        val user = RepositorioDeInicioDeSesion.obtenerUsuarios()
            .find { it.correoElectronico.equals(email.trim(), ignoreCase = true) && it.contraseña == password }
            ?: throw IllegalArgumentException("Email o contraseña incorrectos.")

        return user
    }
}
