package com.numberinfo.features.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.numberinfo.core.db.Numbers
import com.numberinfo.core.presentation.BaseViewModel
import com.numberinfo.core.presentation.UiState
import com.numberinfo.core.utils.SingleLiveEvent
import com.numberinfo.features.home.domain.useCase.GetAllFactsUseCase
import com.numberinfo.features.home.domain.useCase.GetFactUseCase
import okhttp3.ResponseBody

class HomeViewModel(
    private val getFactUseCase: GetFactUseCase,
    private val getAllFactsUseCase: GetAllFactsUseCase
) : BaseViewModel() {

    private val _getFactResponse = SingleLiveEvent<UiState<ResponseBody>>()
    val getFactResponse: LiveData<UiState<ResponseBody>> = _getFactResponse

    val allFacts: LiveData<List<Numbers>> = getAllFactsUseCase.allFacts.asLiveData()

    fun getFact(number: String) {
        launchWithUIState(
            block = { getFactUseCase.invoke(number) },
            liveData = _getFactResponse
        )
    }

    fun getRandomFact() {
        launchWithUIState(
            block = { getFactUseCase.invoke("random") },
            liveData = _getFactResponse
        )
    }
}