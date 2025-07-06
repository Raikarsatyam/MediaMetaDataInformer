package com.example.mediametadatainformer.data

import com.google.gson.annotations.SerializedName

data class VideoMetadata(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("author_name")
    val authorName: String?,
    @SerializedName("author_url")
    val authorUrl: String?,
    @SerializedName("type")
    val type: String,
    @SerializedName("provider_name")
    val providerName: String,
    @SerializedName("provider_url")
    val providerUrl: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("thumbnail_width")
    val thumbnailWidth: Int?,
    @SerializedName("thumbnail_height")
    val thumbnailHeight: Int?,
    @SerializedName("html")
    val html: String?,
    @SerializedName("version")
    val version: String?,
    @SerializedName("duration")
    val duration: Int? = null,
    @SerializedName("upload_date")
    val uploadDate: String? = null,
    @SerializedName("video_id")
    val videoId: String? = null,
    @SerializedName("uri")
    val uri: String? = null
) 