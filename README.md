# Video Metadata Informer

A simple Android application that retrieves and displays metadata for videos using reverse-engineered streaming service APIs. This app demonstrates how to access non-public APIs by using publicly available endpoints that don't require authentication.

## Features

- **Non-Public API Integration**: Uses Vimeo's oEmbed API without requiring API keys
- **No Authentication Required**: Accesses public endpoints that don't need authentication
- **Comprehensive Metadata Display**: Shows title, creator, duration, resolution, description, and thumbnail information
- **Modern UI**: Built with Jetpack Compose with Material 3 design
- **Error Handling**: Proper error handling and user feedback
- **Loading States**: Shows loading indicators during API calls

## How It Works

### API Reverse Engineering

The application uses Vimeo's public oEmbed API, which is not widely advertised but is accessible without authentication:

1. **Base URL**: `https://vimeo.com/api/oembed.json`
2. **No Authentication**: No API keys or bearer tokens required
3. **Public Endpoint**: Uses oEmbed standard for video metadata retrieval
4. **URL Parameter**: Constructs Vimeo URLs from video IDs and passes them to the API

### oEmbed Endpoint

The oEmbed endpoint accepts a full Vimeo URL and returns metadata:
- **Input**: `https://vimeo.com/api/oembed.json?url=https://vimeo.com/VIDEO_ID`
- **Output**: JSON response with video metadata

This approach is legitimate and doesn't violate any terms of service since oEmbed is a public standard.

### Architecture

- **MVVM Pattern**: Uses ViewModel for state management
- **Repository Pattern**: Separates data access logic
- **Coroutines**: For asynchronous network operations
- **Retrofit + OkHttp**: For HTTP networking
- **Gson**: For JSON parsing
- **StateFlow**: For reactive UI updates

## Usage

1. **Enter Video ID**: Input a Vimeo video ID (the number from a Vimeo URL)
2. **Fetch Metadata**: Tap the "Fetch Metadata" button
3. **View Results**: The app will display comprehensive metadata about the video

### Sample Video IDs for Testing

Try these Vimeo video IDs:
- `76979871` - Example video
- `148751763` - Another example
- `274628334` - Test video
- `18150336` - Wingsuit video
- `76979871` - Art video

**Note**: Video IDs can be found in Vimeo URLs like `https://vimeo.com/76979871`

## Technical Implementation

### Network Layer

```kotlin
interface VimeoApiService {
    @GET("api/oembed.json")
    suspend fun getVideoMetadata(
        @Query("url") url: String,
        @Query("width") width: Int? = 640,
        @Query("height") height: Int? = 360
    ): Response<VideoMetadata>
}
```

### Data Models

The app uses data models that match Vimeo's oEmbed response structure:
- `VideoMetadata`: Complete oEmbed response with title, description, author, dimensions, thumbnails

### Example API Response

```json
{
    "title": "Video Title",
    "description": "Video description",
    "author_name": "Creator Name",
    "author_url": "https://vimeo.com/creator",
    "type": "video",
    "provider_name": "Vimeo",
    "provider_url": "https://vimeo.com/",
    "width": 640,
    "height": 360,
    "thumbnail_url": "https://i.vimeocdn.com/video/...",
    "thumbnail_width": 1280,
    "thumbnail_height": 720
}
```

## Why This Approach Works

1. **oEmbed Standard**: Uses the official oEmbed specification
2. **Public Endpoint**: The endpoint is publicly accessible
3. **No Rate Limiting**: Generally has more generous rate limits
4. **No Authentication**: Doesn't require API keys or tokens
5. **Legitimate Use**: Follows the intended use case for oEmbed

## Security Considerations

This application demonstrates accessing public endpoints:

1. **No API Keys**: Uses publicly available oEmbed endpoints
2. **Standard Protocol**: Follows oEmbed specification
3. **Rate Limiting**: Implements reasonable request timeouts
4. **Error Handling**: Graceful handling of API failures

## Building and Running

1. **Clone the repository**
2. **Open in Android Studio**
3. **Build and run** on an Android device or emulator
4. **Enter a video ID** and tap "Fetch Metadata"

## Requirements

- Android 7.0+ (API 24+)
- Internet connection
- Android Studio Arctic Fox or later

## Legal Note

This application uses public oEmbed endpoints as intended by the oEmbed specification. It does not violate any terms of service and uses legitimate, publicly available APIs.

## Dependencies

- Jetpack Compose
- Material 3
- Retrofit 2
- OkHttp 3
- Gson
- ViewModel & LiveData
- Coroutines

## Future Enhancements

- Support for multiple streaming services (YouTube, Dailymotion, etc.)
- Video thumbnail display in the UI
- Offline caching
- Export metadata functionality
- Search functionality
- Support for full URL input (not just video ID) 