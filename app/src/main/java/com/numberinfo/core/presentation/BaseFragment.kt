package com.numberinfo.core.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.numberinfo.features.MainActivity
import java.util.concurrent.atomic.AtomicInteger

abstract class BaseFragment: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    abstract fun initUI()
    abstract fun initObservers()

    protected val sharedProgressNumber = AtomicInteger(0)

    inline fun <T : Any> observeLiveData(liveData: LiveData<T>, crossinline changed: (T) -> Unit) =
        liveData.observe(viewLifecycleOwner) { changed(it) }

    protected open fun <T : Any> handleUIState(
        uiState: UiState<T>,
        handleError: (UiState.Error) -> Unit = ::showError,
        handleSuccess: (T) -> Unit,
    ) {
        when (uiState) {
            is UiState.Loading -> showProgress()
            is UiState.Success -> handleSuccess(uiState.result)
            is UiState.Error -> handleError(uiState)
        }
    }

    open fun showProgress() {
        if (parentFragment is ShareableProgress) {
            (parentFragment as ShareableProgress).showProgress()
        } else if (this is ShareableProgress) {
            incrementProgress()
        } else {
            (activity as? MainActivity)?.showProgress()
        }
    }

    open fun hideProgress() {
        if (parentFragment is ShareableProgress) {
            (parentFragment as ShareableProgress).hideProgress()
        } else if (this is ShareableProgress) {
            decrementProgress()
        } else {
            (activity as? MainActivity)?.hideProgress()
        }
    }

    private fun incrementProgress() {
        if (sharedProgressNumber.incrementAndGet() > 0) {
            (activity as? MainActivity)?.showProgress()
        }
    }

    private fun decrementProgress() {
        if (sharedProgressNumber.get() > 0 && sharedProgressNumber.decrementAndGet() <= 0) {
            completelyHideProgress()
        }
    }

    protected open fun showError(errorState: UiState.Error) {
        hideProgress()
    }

    protected open fun completelyHideProgress() {
        (activity as? MainActivity)?.hideProgress()
    }

}