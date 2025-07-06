package com.example.mediametadatainformer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mediametadatainformer.data.VideoMetadata
import com.example.mediametadatainformer.utils.VideoUtils
import com.example.mediametadatainformer.viewmodel.VideoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoMetadataScreen(
    viewModel: VideoViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var videoId by remember { mutableStateOf("") }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Title
        Text(
            text = "Video Metadata Fetcher",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Video ID Input
        OutlinedTextField(
            value = videoId,
            onValueChange = { videoId = it },
            label = { Text("Enter Video ID") },
            placeholder = { Text("e.g., 76979871") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Helper text
        Text(
            text = "Enter the numeric ID from a Vimeo URL (e.g., from https://vimeo.com/76979871)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Fetch Button
        Button(
            onClick = {
                if (videoId.isNotBlank()) {
                    viewModel.fetchVideoMetadata(videoId.trim())
                }
            },
            enabled = videoId.isNotBlank() && !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Fetch Metadata")
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Error Message
        uiState.errorMessage?.let { error ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Video Metadata Display
        uiState.videoMetadata?.let { metadata ->
            VideoMetadataCard(metadata = metadata)
        }
    }
}

@Composable
fun VideoMetadataCard(metadata: VideoMetadata) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Title
            Text(
                text = metadata.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            // Provider
            MetadataRow(
                label = "Platform",
                value = metadata.providerName
            )
            
            // Author/Creator
            metadata.authorName?.let { authorName ->
                MetadataRow(
                    label = "Creator",
                    value = authorName
                )
            }
            
            // Video Type
            MetadataRow(
                label = "Type",
                value = metadata.type.replaceFirstChar { it.uppercase() }
            )
            
            // Duration
            metadata.duration?.let { duration ->
                MetadataRow(
                    label = "Duration",
                    value = VideoUtils.formatDuration(duration)
                )
            }
            
            // Video Resolution
            MetadataRow(
                label = "Video Resolution",
                value = VideoUtils.formatResolution(metadata.width, metadata.height)
            )
            
            // Thumbnail Resolution
            if (metadata.thumbnailWidth != null && metadata.thumbnailHeight != null) {
                MetadataRow(
                    label = "Thumbnail Resolution",
                    value = VideoUtils.formatThumbnailResolution(metadata.thumbnailWidth, metadata.thumbnailHeight)
                )
            }
            
            // Upload Date
            metadata.uploadDate?.let { uploadDate ->
                MetadataRow(
                    label = "Uploaded",
                    value = VideoUtils.formatDate(uploadDate)
                )
            }
            
            // Description
            metadata.description?.let { description ->
                if (description.isNotBlank()) {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 20.sp
                    )
                }
            }
            
            // Author URL
            metadata.authorUrl?.let { authorUrl ->
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                MetadataRow(
                    label = "Creator Profile",
                    value = authorUrl
                )
            }
            
            // Thumbnail URL
            metadata.thumbnailUrl?.let { thumbnailUrl ->
                MetadataRow(
                    label = "Thumbnail URL",
                    value = thumbnailUrl
                )
            }
            
            // Provider URL
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            MetadataRow(
                label = "Platform URL",
                value = metadata.providerUrl
            )
        }
    }
}

@Composable
fun MetadataRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
} 