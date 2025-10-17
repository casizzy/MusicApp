package com.pjsoft.libraryapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.io.Serializable

data class Album(
    val id: Int,
    val title: String,
    val artist: String,
    val imageUrl: String,
    val description: String
) : Serializable

val PurpleDark = Color(0xFF2B134F)
val Purple = Color(0xFF7B61FF)
val Bg = Color(0xFFF3ECFF)
val TextPrimary = Color(0xFF111111)
val TextSecondary = Color(0xFF6B6B6B)


@Composable
fun HomeScreen(
    name: String = "Stella Casillas",
    albums: List<Album> = sampleAlbums,
    recently: List<Album> = sampleAlbums.shuffled(),
    onAlbumClick: (Album) -> Unit = {}
) {
    var isPlaying by remember { mutableStateOf(true) }
    val current = albums.first()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 96.dp)
        ) {
            HeaderHome(name)
            SectionHeader("Albums", "See more")

            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(albums) { album ->
                    AlbumCard(album) { onAlbumClick(album) }
                }
            }

            Spacer(Modifier.height(12.dp))
            SectionHeader("Recently Played", "See more")

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(recently) { album ->
                    RecentlyPlayedItem(album) { onAlbumClick(album) }
                }
                item { Spacer(Modifier.height(30.dp)) }
            }
        }

        MiniPlayer(
            album = current,
            isPlaying = isPlaying,
            onPlayPause = { isPlaying = !isPlaying },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}


@Composable
private fun HeaderHome(name: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(
                Brush.verticalGradient(
                    listOf(Purple, Color(0xFFA58BFF))
                )
            )
            .padding(20.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(Icons.Default.Menu, null, tint = Color.White)
            Icon(Icons.Default.Search, null, tint = Color.White)
        }
        Column(Modifier.align(Alignment.CenterStart)) {
            Text("Good Morning!", color = Color.White.copy(alpha = 0.9f))
            Text(name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 26.sp)
        }
    }
}

@Composable
fun SectionHeader(title: String, action: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text(action, color = Purple, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun AlbumCard(album: Album, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 260.dp, height = 210.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = album.imageUrl,
            contentDescription = album.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color(0xAA000000))
                    )
                )
                .padding(16.dp)
        ) {
            Column(Modifier.align(Alignment.BottomStart)) {
                Text(album.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(album.artist, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
            }
            Surface(
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.3f),
                shadowElevation = 8.dp,
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(Icons.Default.PlayArrow, null, tint = Color.White, modifier = Modifier.padding(10.dp))
            }
        }
    }
}

@Composable
fun RecentlyPlayedItem(album: Album, onClick: () -> Unit) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(18.dp),
        tonalElevation = 2.dp,
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = album.imageUrl,
                contentDescription = album.title,
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(album.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("${album.artist} • Popular Song", color = TextSecondary, fontSize = 14.sp)
            }
            Icon(Icons.Default.MoreVert, null, tint = TextSecondary)
        }
    }
}

@Composable
fun MiniPlayer(album: Album, isPlaying: Boolean, onPlayPause: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        color = PurpleDark,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = album.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(album.title, color = Color.White, fontWeight = FontWeight.Bold)
                Text(album.artist, color = Color.White.copy(alpha = 0.8f))
            }
            FilledIconButton(onClick = onPlayPause, colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.White)) {
                Icon(
                    if (isPlaying) Icons.Rounded.Pause else Icons.Default.PlayArrow,
                    null,
                    tint = PurpleDark
                )
            }
        }
    }
}

val sampleAlbums = listOf(
    Album(1, "Tales of Ithiria", "Haggard", "https://", "Álbum sinfónico."),
    Album(2, "Awake", "Avenged Sevenfold", "https://", "Metal melódico."),
    Album(3, "Nightmare", "Avenged Sevenfold", "https://", "Clásico moderno."),
    Album(4, "Abbey Road", "The Beatles", "https://", "Leyenda británica.")
)
