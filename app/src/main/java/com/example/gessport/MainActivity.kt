package com.example.gessport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gessport.ui.home.HomeScreen // Importación necesaria para HomeScreen
import com.example.gessport.ui.login.LoginScreen
import com.example.gessport.ui.theme.GesSportTheme

// Define las rutas
object Routes {
    const val Login = "login"
    // MODIFICADO: Ahora Home incluye el argumento {nombre}
    const val Home = "home"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GesSportTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Login,
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Ruta para la pantalla de Login
        composable(Routes.Login) {
            LoginScreen(navController = navController)
        }

        // 2. Ruta para la pantalla de Home (MODIFICADA)
        composable(
            route = "${Routes.Home}/{nombre}", // La ruta ahora espera el argumento
            arguments = listOf(
                navArgument("nombre") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            // Se extrae el argumento 'nombre' y se pasa al Composable HomeScreen
            HomeScreen(
                navController = navController,
                nombre = backStackEntry.arguments?.getString("nombre")
            )
        }
    }
}