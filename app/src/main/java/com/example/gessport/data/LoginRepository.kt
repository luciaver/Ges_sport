package com.example.gessport.data

import com.example.gessport.models.User


object RepositorioDeInicioDeSesion {

    private val usuarios: List<User> = listOf(
        User(1, "Ana López", "ana@correo.com", "1234", "admin"),
        User(2, "Luis Gómez", "luis@correo.com", "abcd", "entrenador"),
        User(3, "María Pérez", "maria@correo.com", "contras1", "entrenador"),
        User(4, "Carlos Ruiz", "carlos@correo.com", "pass2", "jugador"),
        User(5, "Laura Díaz", "laura@correo.com", "laura123", "jugador"),
        User(6, "Javier Torres", "javier@correo.com", "javi2025", "jugador"),
        User(7, "Sofía Sánchez", "sofia@correo.com", "sofia!", "admin"),
        User(8, "Miguel Fernández", "miguel@correo.com", "clave", "jugador"),
        User(9, "Elena Ramírez", "elena@correo.com", "hola123", "jugador"),
        User(10, "Pedro Martín", "pedro@correo.com", "pedro321", "admin")
    )

    fun obtenerUsuarios(): List<User> = usuarios
}