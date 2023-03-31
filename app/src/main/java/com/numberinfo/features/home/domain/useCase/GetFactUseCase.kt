package com.numberinfo.features.home.domain.useCase

import com.numberinfo.core.db.NumbersDao
import com.numberinfo.core.db.factToDb
import com.numberinfo.network.NumberRepository
import com.numberinfo.network.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class GetFactUseCase(
    private val repository: NumberRepository,
    private val numbersDao: NumbersDao,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        number: String
    ): Result<ResponseBody> =
        withContext(dispatcher) {
            try {
                val resultResponse = repository.getFact(number)

                if (resultResponse.isSuccess) {
                    numbersDao.insertNumber(factToDb(resultResponse.getOrNull()?.string()))
                    return@withContext resultResponse
                } else {
                    return@withContext Result.Failure(Exception("Incorrect fact!"))
                }
            } catch (e: Exception) {
                return@withContext Result.Failure(e)
            }
        }
}