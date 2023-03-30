package com.numberinfo.features.home.domain.useCase

import com.numberinfo.core.db.Numbers
import com.numberinfo.core.db.NumbersDao
import kotlinx.coroutines.flow.Flow

class GetAllFactsUseCase(
    private val numbersDao: NumbersDao,
) {
    val allFacts: Flow<List<Numbers>> = numbersDao.gelAllNumbers()
}