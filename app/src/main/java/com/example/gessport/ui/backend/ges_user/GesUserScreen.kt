package com.example.gessport.ui.backend.ges_user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
fun GesUserScreen(navController: NavHostController) {
    val viewModel: GesUserViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repo = DataUserRepository.DataUserRepository()
                return GesUserViewModel(repo) as T
            }
        }
    )

    val users = viewModel.users
    val selectedRole = viewModel.selectedRole

    val redPrimary = Color(0xFFFF0000)
    val grayBackground = Color(0xFFE0E0E0)
    val whiteCard = Color(0xFFFFFFFF)

    Scaffold(
        containerColor = grayBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Gestión de Usuarios",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Filtros por rol
            Text(
                text = "Filtrar por rol:",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                FilterChip(
                    selected = selectedRole == null,
                    onClick = { viewModel.onRoleSelected(null) },
                    label = { Text("TODOS") },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color.White,
                        labelColor = Color.Black,
                        selectedContainerColor = redPrimary,
                        selectedLabelColor = Color.White
                    )
                )

                UserRoles.allRoles.forEach { (roleKey, roleLabel) ->
                    FilterChip(
                        selected = selectedRole == roleKey,
                        onClick = {
                            val newRole = if (selectedRole == roleKey) null else roleKey
                            viewModel.onRoleSelected(newRole)
                        },
                        label = { Text(roleLabel) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color.White,
                            labelColor = Color.Black,
                            selectedContainerColor = redPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { navController.navigate("adduser") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Añadir", fontSize = 14.sp)
                }

                Button(
                    onClick = {
                        navController.navigate("selectuser")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Modificar", fontSize = 14.sp)
                }

                Button(
                    onClick = {
                        navController.navigate("deleteuser")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = redPrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Eliminar", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de usuarios
            if (users.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay usuarios para mostrar",
                        color = Color.Black.copy(alpha = 0.6f),
                        fontSize = 16.sp
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(users) { user ->
                        UserListItem(user = user, whiteCard = whiteCard)
                    }
                }
            }
        }
    }
}

@Composable
fun UserListItem(user: User, whiteCard: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = whiteCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = user.nombre,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = user.email,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Rol: ${getRoleName(user.rol)}",
                color = Color(0xFFFF0000),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

fun getRoleName(roleKey: String): String {
    return when (roleKey) {
        "ADMIN_DEPORTIVO" -> "Admin Deportivo"
        "ENTRENADOR" -> "Entrenador"
        "ARBITRO" -> "Árbitro"
        "JUGADOR" -> "Jugador"
        else -> roleKey
    }
}