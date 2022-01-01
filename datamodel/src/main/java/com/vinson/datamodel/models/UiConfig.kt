package com.vinson.datamodel.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiConfig(
    @SerializedName("size") val size: Int,
    @SerializedName("layout_style") val style: String,
    @SerializedName("can_download") val canDownload: Boolean,
) : Parcelable {

    companion object {
        val DefaultUiConfig = UiConfig(40, "linear", true)
    }
}
