package com.vinson.datamodel.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.vinson.datamodel.base.BaseResponse
import com.vinson.datamodel.base.ServerErrorException
import com.vinson.datamodel.models.ImageResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchImageResultResponse(
        @SerializedName("total") private val _total: Int,
        @SerializedName("totalHits") val totalHit: Int,
        @SerializedName("hits") private val hits: List<ImageResult>
) : Parcelable, BaseResponse<List<ImageResult>> {

    override fun isSuccess() = hits.isNotEmpty()

    override fun getData() = hits

    override fun getError() = ServerErrorException(403, "error")
}
