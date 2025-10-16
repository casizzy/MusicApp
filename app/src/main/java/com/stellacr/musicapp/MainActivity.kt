package com.stellacr.musicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pjsoft.libraryapp.screens.Album
import com.pjsoft.libraryapp.screens.HomeScreen
import com.stellacr.musicapp.screens.DetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicApp()
        }
    }
}

@Composable
fun MusicApp() {
    val navController = rememberNavController()

    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            NavHost(navController = navController, startDestination = "home") {

                composable("home") {
                    HomeScreen(
                        name = "Stella Casillas",
                        onAlbumClick = { album ->
                            navController.currentBackStackEntry?.savedStateHandle?.set("album", album)
                            navController.navigate("detail")
                        }
                    )
                }

                composable("detail") {
                    val album = navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<Album>("album")

                    if (album != null) {
                        DetailScreen(
                            album = album,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
