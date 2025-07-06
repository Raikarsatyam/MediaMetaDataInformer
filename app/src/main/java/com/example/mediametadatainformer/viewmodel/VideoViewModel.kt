package com.example.mediametadatainformer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediametadatainformer.data.VideoMetadata
import com.example.mediametadatainformer.repository.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {
    
    private val repository = VideoRepository()
    
    private val _uiState = MutableStateFlow(VideoUiState())
    val uiState: StateFlow<VideoUiState> = _uiState.asStateFlow()
    
    fun fetchVideoMetadata(videoId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            repository.getVideoMetadata(videoId)
                .onSuccess { metadata ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        videoMetadata = metadata,
                        errorMessage = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Unknown error occurred"
                    )
                }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

data class VideoUiState(
    val isLoading: Boolean = false,
    val videoMetadata: VideoMetadata? = null,
    val errorMessage: String? = null
) 