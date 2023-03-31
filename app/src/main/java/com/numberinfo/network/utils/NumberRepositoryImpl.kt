package com.numberinfo.network.utils

import com.numberinfo.network.NumberRepository
import com.numberinfo.network.NumbersAPI
import okhttp3.ResponseBody

class NumberRepositoryImpl(private val numbersAPI: NumbersAPI) : NumberRepository {

    override suspend fun getFact(number: String): Result<ResponseBody> {
        return requestApiCall { numbersAPI.getFact(number) }
    }
}