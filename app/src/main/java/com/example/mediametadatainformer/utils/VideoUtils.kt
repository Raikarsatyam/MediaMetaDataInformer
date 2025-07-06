package com.example.mediametadatainformer.utils

import java.text.SimpleDateFormat
import java.util.*

object VideoUtils {
    
    fun formatDuration(durationInSeconds: Int?): String {
        if (durationInSeconds == null) return "N/A"
        
        val hours = durationInSeconds / 3600
        val minutes = (durationInSeconds % 3600) / 60
        val seconds = durationInSeconds % 60
        
        return when {
            hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
            else -> String.format("%d:%02d", minutes, seconds)
        }
    }
    
    fun formatDate(dateString: String?): String {
        if (dateString == null) return "N/A"
        
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SS:SS", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            // Try alternative format
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val date = inputFormat.parse(dateString)
                outputFormat.format(date ?: Date())
            } catch (e: Exception) {
                dateString.split("T")[0] // Return just the date part if parsing fails
            }
        }
    }
    
    fun formatResolution(width: Int, height: Int): String {
        return "${width}x${height}"
    }
    
    fun formatThumbnailResolution(width: Int?, height: Int?): String {
        return if (width != null && height != null) {
            "${width}x${height}"
        } else {
            "N/A"
        }
    }
    
    fun getVideoIdFromUrl(url: String): String? {
        return try {
            // Extract video ID from various Vimeo URL formats
            val patterns = listOf(
                Regex("vimeo\\.com/(\\d+)"),
                Regex("vimeo\\.com/channels/.+/(\\d+)"),
                Regex("vimeo\\.com/groups/.+/videos/(\\d+)")
            )
            
            for (pattern in patterns) {
                val match = pattern.find(url)
                if (match != null) {
                    return match.groupValues[1]
                }
            }
            null
        } catch (e: Exception) {
            null
        }
    }
} 