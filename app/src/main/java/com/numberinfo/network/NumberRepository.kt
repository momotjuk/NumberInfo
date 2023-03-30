package com.numberinfo.network

import com.numberinfo.network.utils.Result
import okhttp3.ResponseBody

interface NumberRepository {
    suspend fun getFact(number: String): Result<ResponseBody>
}