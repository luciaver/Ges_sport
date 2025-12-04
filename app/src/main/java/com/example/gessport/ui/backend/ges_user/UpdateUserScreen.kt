package com.example.gessport.ui.backend.ges_user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gessport.data.DataUserRepository
import com.example.gessport.models.User
import com.example.gessport.models.UserRoles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserScreen(navController: NavHostController, userId: Int) {
    val viewModel: GesUserViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repo = DataUserRepository.DataUserRepository()
                return GesUserViewModel(repo) as T
            }
        }
    )

    LaunchedEffect(userId) {
        viewModel.getUserById(userId)
    }

    val userToEdit = viewModel.userToEdit

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("JUGADOR") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(userToEdit) {
        userToEdit?.let {
            nombre = it.nombre
            email = it.email
            password = it.password
            selectedRole = it.rol
        }
    }

    val redPrimary = Color(0xFFFF0000)
    val grayBackground = Color(0xFFE0E0E0)
    val whiteCard = Color(0xFFFFFFFF)

    Scaffold(
        containerColor = grayBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Modificar Usuario",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = redPrimary
                )
            )
        }
    ) { paddingValues ->
        if (userToEdit == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = redPrimary)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // Campo Nombre
                Text(
                    "Nombre completo",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    placeholder = { Text("Introduce el nombre") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = whiteCard,
                        unfocusedContainerColor = whiteCard,
                        focusedBorderColor = redPrimary,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = redPrimary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo Email
                Text(
                    "Email",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Introduce el email") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = whiteCard,
                        unfocusedContainerColor = whiteCard,
                        focusedBorderColor = redPrimary,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = redPrimary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo Contraseña
                Text(
                    "Contraseña",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Introduce la contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = whiteCard,
                        unfocusedContainerColor = whiteCard,
                        focusedBorderColor = redPrimary,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = redPrimary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                //Aqui seelccionamos el rol que va a tenr el nuevo usuario
                Text(
                    "Selecciona el rol:",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                UserRoles.allRoles.forEach { (roleKey, roleLabel) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedRole == roleKey,
                            onClick = { selectedRole = roleKey },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = redPrimary,
                                unselectedColor = Color.Gray
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = roleLabel,
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botón Actualizar
                Button(
                    onClick = {
                        if (nombre.isBlank() || email.isBlank() || password.isBlank()) {
                            errorMessage = "Todos los campos son obligatorios"
                        } else {
                            val updatedUser = User(
                                id = userId,
                                nombre = nombre,
                                email = email,
                                password = password,
                                rol = selectedRole
                            )
                            viewModel.updateUser(updatedUser)
                            navController.popBackStack()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Actualizar Usuario",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage!!,
                        color = redPrimary
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}