package com.numberinfo.network.utils

import java.lang.Exception

sealed class Result<T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Failure<T>(val error: Exception) : Result<T>()

    val isSuccess: Boolean get() = this is Success
    val isFailure: Boolean get() = this is Failure

    fun getOrNull(): T? = when (this) {
        is Success -> value
        is Failure -> null
    }

    inline fun onSuccess(block: (T) -> Unit) {
        getOrNull()?.let(block)
    }

    fun getOrThrow(): T =
        when (this) {
            is Success -> value
            is Failure -> throw error
        }

    fun exceptionOrNull(): Throwable? =
        when (this) {
            is Failure -> error
            is Success -> null
        }
}

inline fun <R, T> Result<T>.map(transform: (value: T) -> R): Result<R> {
    return when (this) {
        is Result.Success -> Result.Success(transform(value))
        is Result.Failure -> Result.Failure(error)
    }
}