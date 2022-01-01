package com.vinson.apimodule.api

import com.vinson.apimodule.builder.ApiType
import com.vinson.apimodule.builder.getOkHttpClient
import com.vinson.datamodel.api.SearchImageResultResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("api/")
    suspend fun queryHits(
        @Query("page") page: Int,
        @Query("q") q: String,
        @Query("image_type") type: String = "all",
        @Query("per_page") perPage: Int = 20
    ): Response<SearchImageResultResponse>

    companion object {

        private fun getBaseUrl() = "https://pixabay.com/"

        fun create(): PixabayApi {
            return Retrofit.Builder()
                    .baseUrl(getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient(ApiType.Photo))
                    .build()
                    .create(PixabayApi::class.java)
        }
    }
}