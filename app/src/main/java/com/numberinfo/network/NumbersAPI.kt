package com.numberinfo.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersAPI {
    @GET("{number}")
    suspend fun getFact(@Path("number") number: String): Response<ResponseBody>
}