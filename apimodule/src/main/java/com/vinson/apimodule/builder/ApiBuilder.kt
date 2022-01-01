package com.vinson.apimodule.builder

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

fun getOkHttpClient(type: ApiType): OkHttpClient {
    return OkHttpClient().newBuilder().apply {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        addInterceptor(interceptor)
        addInterceptor(getHeaderInterceptor(type))
    }.build()
}

private fun getHeaderInterceptor(type: ApiType): Interceptor {
    return Interceptor { chain ->
        var request = chain.request()

        var url = request.url()
        var body = request.body()

        when (type) {
            ApiType.Photo -> url = request.createUrlWithParams()
            else -> Unit
        }

        request = request.newBuilder().apply {
            url(url)
            method(request.method(), body)
        }.build()


        Log.d("OkHttp", request.headers().toString())
        chain.proceed(request)
    }
}

private fun Request.createUrlWithParams() = url().newBuilder()
        .addQueryParameter("key", "24984954-f4909580a263c8ede5aae3a45")
        .build()

enum class ApiType {
    Photo
}