package com.example.mediametadatainformer.network

import com.example.mediametadatainformer.data.VideoMetadata
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VimeoApiService {
    
    @GET("api/oembed.json")
    suspend fun getVideoMetadata(
        @Query("url") url: String
    ): Response<VideoMetadata>
} 