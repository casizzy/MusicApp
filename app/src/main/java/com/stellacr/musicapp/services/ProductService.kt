package com.stellacr.musicapp.services

import com.stellacr.musicapp.models.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("api/albums")
    suspend fun getAlbums(): List<Album>

    @GET("api/albums/{id}")
    suspend fun getAlbumDetail(@Path("id") id: String): Album
}