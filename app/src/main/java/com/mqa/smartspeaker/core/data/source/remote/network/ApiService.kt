package com.mqa.smartspeaker.core.data.source.remote.network

import com.mqa.smartspeaker.core.data.source.remote.response.ListTourismResponse
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("list")
    suspend fun getList(): ListTourismResponse

    @POST("api/register")
    suspend fun postRegister(@Body requestBody: RegisterRequest): Response<RegisterResponse>
}
