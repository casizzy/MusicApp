package com.stellacr.musicapp

import com.stellacr.musicapp.screens.HomeScreen
import com.stellacr.musicapp.screens.sampleAlbums
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
                        albums = sampleAlbums,
                        recently = sampleAlbums.shuffled(),
                        onAlbumClick = {
                        }
                    )
                }

            }
        }
    }
}
