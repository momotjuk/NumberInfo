package com.numberinfo.network.utils

import kotlinx.coroutines.runBlocking
import retrofit2.Response


suspend fun <T> requestApiCall(
    call: suspend () -> Response<T>
): Result<T> {
    return try {
        runBlocking {
            val response = call.invoke()
            if (response.isSuccessful) {
                response.body()?.let { Result.Success(it) } ?: Result.Failure(
                    Exception("Failed to parse model")
                )
            } else {
                Result.Failure(
                    Exception("Failed to parse model")
                )
            }
        }
    } catch (throwable: Throwable) {
        Result.Failure(Exception(throwable))
    }
}