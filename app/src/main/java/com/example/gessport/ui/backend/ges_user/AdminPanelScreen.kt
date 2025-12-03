package com.example.gessport.ui.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gessport.R // Necesario para acceder a tus recursos (R.drawable.*)


@Composable
fun AdminPanelScreen(navController: NavController) {
    val redPrimary = Color(0xFFFF0000)
    val grayBackground = Color(0xFFE0E0E0)

    // NOTA: Asegúrate de que los nombres de los archivos en R.drawable.* coincidan
    // con los nombres reales en tu carpeta res/drawable.
    // Usaremos Icons.Default.* para los que no tienen imagen cargada.

    Surface(
        color = grayBackground,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabecera roja
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(redPrimary, RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Panel de Administración",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Grid de opciones (3 columnas, 2 filas)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Primera fila
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AdminCard(
                        title = "Jugadores",
                        iconVector = Icons.Default.Person, // Usa Vector Icon
                        modifier = Modifier.weight(1f),
                        onClick = { /* Navegar a jugadores */ }
                    )
                    AdminCard(
                        title = "Equipos",
                        iconDrawable = R.drawable.equipo, // Usa tu imagen 'equipo.png'
                        modifier = Modifier.weight(1f),
                        onClick = { /* Navegar a equipos */ }
                    )
                    AdminCard(
                        title = "Partidos",
                        iconDrawable = R.drawable.bal, // Usa Vector Icon
                        modifier = Modifier.weight(1f),
                        onClick = { /* Navegar a partidos */ }
                    )
                }

                // Segunda fila
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AdminCard(
                        title = "Pistas",
                        iconDrawable = R.drawable.campo, // Usa tu imagen 'campo.png'
                        modifier = Modifier.weight(1f),
                        onClick = { /* Navegar a pistas */ }
                    )
                    AdminCard(
                        title = "Reserva",
                        iconDrawable = R.drawable.reserva, // Usa tu imagen 'reserva.png'
                        modifier = Modifier.weight(1f),
                        onClick = { /* Navegar a reservas */ }
                    )
                    AdminCard(
                        title = "Usuarios",
                        iconDrawable = R.drawable.bal, // Usa Vector Icon
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("gesuser") }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón cerrar sesión
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = redPrimary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(56.dp)
            ) {
                Text(
                    text = "CERRAR SESIÓN",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun AdminCard(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    // Acepta un ImageVector (Icono vectorial) o un Int (Recurso de imagen)
    iconVector: ImageVector? = null,
    iconDrawable: Int? = null
) {
    val grayCard = Color(0xFFC0C0C0)

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = grayCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Lógica para mostrar Icono o Imagen
            when {
                iconDrawable != null -> {
                    Image(
                        painter = painterResource(id = iconDrawable),
                        contentDescription = title,
                        modifier = Modifier.size(48.dp) // Las imágenes suelen necesitar un tamaño un poco mayor
                    )
                }
                iconVector != null -> {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = title,
                        tint = Color.Black,
                        modifier = Modifier.size(36.dp)
                    )
                }
                // Puedes añadir un caso por defecto o un error si ambos son null
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}