package com.numberinfo.core.presentation

sealed class UiState<out T : Any> {

    object Loading : UiState<Nothing>()
    data class Error(val error: Throwable? = null) : UiState<Nothing>() {
        fun getMessage(): String {
            return error?.message ?: "Unknown error"
        }
    }
    data class Success<out T : Any>(val result: T) : UiState<T>()
}