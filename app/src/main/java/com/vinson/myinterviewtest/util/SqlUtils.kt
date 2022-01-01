package com.vinson.myinterviewtest.util

import com.vinson.datamodel.base.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeSqlCall(action: suspend () -> T): Result<T> {
    return try {
        withContext(Dispatchers.IO) {
            Result.Success(action())
        }
    } catch (e: Exception) {
        Result.Error(e)
    }
}