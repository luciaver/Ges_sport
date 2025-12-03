package com.example.gessport.ui.backend.ges_user

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gessport.data.DataUserRepository
import com.example.gessport.models.UserRoles

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GesUserScreen(
    navController: NavHostController
)  {
    // 1) Le pido a Compose el contexto actual (por si lo necesitáramos)
    val context = LocalContext.current
    // 2) Creo el ViewModel indicándole una factory
    val viewModel: GesUserViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                // 2.1) Aquí decido cómo construir el REPOSITORIO
                val repo = DataUserRepository()

                // 2.2) Aquí decido cómo construir el VIEWMODEL
                return GesUserViewModel(repo) as T
            }
        }
    )
    //Lista de USAURIOS y el rol
    val users = viewModel.users
    val selectedRole = viewModel.selectedRole


    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("formuser") },
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir usuario"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End, // abajo derecha
    ) {
        Column {
            // Aquí irían tus chips (Administrador deportivo, Entrenador, Árbitro, Jugador)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Chip "Todos"
                FilterChip(
                    selected = selectedRole == null,
                    onClick = { viewModel.onRoleSelected(null) },
                    label = { Text("TODOS") },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color.Black,
                        labelColor = Color.White,
                        selectedContainerColor = Color.Black,
                        selectedLabelColor = Color.White
                    ),
                    border = null
                )
                UserRoles.allRoles.forEach { (roleKey, roleLabel) ->
                    FilterChip(
                        selected = selectedRole == roleKey,
                        onClick = {
                            val newRole = if (selectedRole == roleKey) null else roleKey
                            //Llamamos a la función del viewModel para devolver la nueva Lista y que se cargue
                            viewModel.onRoleSelected(newRole)
                        },
                        label = { Text(roleLabel) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color.Black,
                            labelColor = Color.White,
                            selectedContainerColor = Color.Black,
                            selectedLabelColor = Color.White
                        ),
                        border = null
                    )
                }



            }

            // Listado
            LazyColumn {
                items(users) { user ->
                    Text(text = "${user.nombre} - ${user.rol}")
                }
            }
        }
    }
}