package com.example.mediametadatainformer.repository

import com.example.mediametadatainformer.data.VideoMetadata
import com.example.mediametadatainformer.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepository {
    
    private val apiService = NetworkClient.vimeoApiService
    
    suspend fun getVideoMetadata(videoId: String): Result<VideoMetadata> {
        return withContext(Dispatchers.IO) {
            try {
                // Construct the Vimeo URL from the video ID
                val vimeoUrl = "https://vimeo.com/$videoId"
                
                val response = apiService.getVideoMetadata(vimeoUrl)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Failed to fetch video metadata: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
} 