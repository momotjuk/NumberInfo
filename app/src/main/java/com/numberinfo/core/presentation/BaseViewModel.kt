package com.numberinfo.core.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.numberinfo.BuildConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.numberinfo.network.utils.Result

abstract class BaseViewModel : ViewModel() {

    protected open fun <T : Any> launchWithUIState(
        block: suspend CoroutineScope.() -> Result<T>,
        liveData: MutableLiveData<UiState<T>>
    ): Job {
        return launchCoroutine(
            block,
            onStart = { liveData.postValue(UiState.Loading) },
            onError = { liveData.postValue(UiState.Error(it)) },
            onSuccess = { liveData.postValue(UiState.Success(it)) }
        )
    }

    protected open fun <T, R : Any> launchWithUIState(
        block: suspend CoroutineScope.() -> Result<T>,
        liveData: MutableLiveData<UiState<R>>,
        transform: (T) -> R
    ): Job {
        return launchCoroutine(
            block,
            onStart = { liveData.postValue(UiState.Loading) },
            onError = { liveData.postValue(UiState.Error(it)) },
            onSuccess = { liveData.postValue(UiState.Success(transform(it))) }
        )
    }

    protected open fun <T> launchCoroutine(
        block: suspend CoroutineScope.() -> Result<T>,
        onSuccess: (T) -> Unit,
        onError: (Throwable?) -> Unit = ::handleError,
        onStart: () -> Unit = {},
        onEnd: () -> Unit = {}
    ) = launch(onError) {
        onStart()
        val result = block()
        when {
            result.isSuccess -> result.getOrNull()?.let(onSuccess)
            result.isFailure -> onError(result.exceptionOrNull())
        }
        onEnd()
    }

    protected open fun launch(
        onError: (Throwable) -> Unit,
        block: suspend CoroutineScope.() -> Unit
    ): Job =
        viewModelScope.launch(CoroutineExceptionHandler { _, exception -> onError(exception) }) {
            block()
        }

    protected open fun handleError(throwable: Throwable?) {
        logError(throwable)
    }

    protected open fun logError(throwable: Throwable?) {
        if (BuildConfig.DEBUG) {
            Log.e("Error --->", "${throwable?.message}")
        }
    }
}
