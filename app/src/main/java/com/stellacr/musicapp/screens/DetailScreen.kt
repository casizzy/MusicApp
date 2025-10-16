package com.stellacr.musicapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pjsoft.libraryapp.screens.*

@Composable
fun DetailScreen(
    album: Album,
    onBack: () -> Unit = {}
) {
    var playing by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 96.dp)
        ) {
            item { HeaderDetail(album, onBack) }

            item {
                AboutCard(album.description)
                Spacer(Modifier.height(16.dp))
                ArtistCard(album.artist)
                Spacer(Modifier.height(20.dp))
            }

            items((1..10).map { "${album.title} • Track $it" }) { track ->
                TrackItem(album, track)
            }
            item { Spacer(Modifier.height(24.dp)) }
        }

        MiniPlayer(
            album = album,
            isPlaying = playing,
            onPlayPause = { playing = !playing },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

/* ---------- COMPONENTES ---------- */

@Composable
private fun HeaderDetail(album: Album, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(28.dp))
            .height(300.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = album.imageUrl,
            contentDescription = album.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, PurpleDark.copy(alpha = 0.9f))
                    )
                )
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(shape = CircleShape, color = Color.Black.copy(alpha = 0.4f)) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                }
            }
            Surface(shape = CircleShape, color = Color.Black.copy(alpha = 0.4f)) {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.FavoriteBorder, null, tint = Color.White)
                }
            }
        }

        Column(
            Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp)
        ) {
            Text(album.title, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
            Text(album.artist, color = Color.White.copy(alpha = 0.9f))
            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FilledIconButton(
                    onClick = {},
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Purple),
                    modifier = Modifier.size(56.dp),
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
                }

                FilledIconButton(
                    onClick = {},
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.White.copy(alpha = 0.7f)),
                    modifier = Modifier.size(56.dp),
                ) {
                    Icon(Icons.Default.Pause, contentDescription = "Pause", tint = Color.White)
                }
            }
        }
    }
}

@Composable
private fun AboutCard(description: String) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 8.dp,
        tonalElevation = 2.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        Column(Modifier.padding(18.dp)) {
            Text("About this album", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextPrimary)
            Spacer(Modifier.height(6.dp))
            Text(description, color = TextSecondary)
        }
    }
}

@Composable
private fun ArtistCard(artist: String) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 2.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        Row(
            Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Artist:", color = Purple, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.width(6.dp))
            Text(artist, color = TextPrimary, fontSize = 15.sp)
        }
    }
}

@Composable
private fun TrackItem(album: Album, title: String) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(18.dp),
        tonalElevation = 1.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clickable { }
    ) {
        Row(
            Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = album.imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(album.artist, color = TextSecondary, fontSize = 14.sp)
            }
        }
    }
}
