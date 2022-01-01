package com.vinson.datamodel.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Parcelize
data class ImageResult(
        @SerializedName("id") val id: Int,
        @SerializedName("pageURL") val pageUrl: String,
        @SerializedName("type") val type: String,
        @SerializedName("tags") private val _tags: String?,
        @SerializedName("previewURL") val previewUrl: String,
        @SerializedName("previewWidth") val width: Int,
        @SerializedName("previewHeight") val height: Int,
        @SerializedName("largeImageURL") val largeImageUrl: String,
        @SerializedName("views") val views: Int,
        @SerializedName("downloads") val downloads: Int,
        @SerializedName("likes") val likes: Int,
        @SerializedName("user_id") val userId: Int,
        @SerializedName("user") val userName: String,
        @SerializedName("userImageURL") val userImageUrl: String,
) : Parcelable {

    @IgnoredOnParcel
    val splitTags
        get() = _tags?.split(", ") ?: emptyList()
}