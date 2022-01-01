package com.vinson.myinterviewtest.model

import com.vinson.datamodel.models.UiConfig
import kotlinx.coroutines.delay
import java.lang.Math.random
import com.vinson.datamodel.base.Result

class UiConfigDataSource {

    suspend fun getLayoutConfig(): Result<UiConfig> {
        delay(1000L)
        val size = if (random() > 0.5f) 40 else 50
        val style = if (random() > 0.5f) "grid" else "linear"
        val canDownload = random() > 0.5f
        return Result.Success(UiConfig(size, style, canDownload))
    }
}