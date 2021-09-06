package com.mqa.smartspeaker.core.data.source.remote.network

import com.mqa.smartspeaker.core.data.source.remote.request.LoginRequest
import com.mqa.smartspeaker.core.data.source.remote.request.RecoveryPasswordRequest
import com.mqa.smartspeaker.core.data.source.remote.request.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.request.UpdateProfileRequest
import com.mqa.smartspeaker.core.data.source.remote.response.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("list")
    suspend fun getList(): ListTourismResponse

    @GET("api/user")
    suspend fun getUser(@Header("Authorization") authHeader:String): Response<User>

    @POST("api/updateProfile")
    suspend fun postUpdateProfile(@Header("Authorization") authHeader:String, @Body requestBody: UpdateProfileRequest): Response<RegularResponse>

    @POST("api/register")
    suspend fun postRegister(@Body requestBody: RegisterRequest): Response<RegisterResponse>

    @GET("api/user/verify/{email}/{verification_code}")
    suspend fun getVerifyEmail(@Path("email") email: String,@Path("verification_code") verificationCode: Int): Response<VerifyEmailResponse>

    @POST("api/login")
    suspend fun postLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("api/forgetPassword")
    suspend fun postForgetPassword(@Body recoveryPasswordRequest: RecoveryPasswordRequest): Response<RegularResponse>

    @POST("api/checkForgetPasswordCode")
    suspend fun postCheckForgetPasswordCode(@Body recoveryPasswordRequest: RecoveryPasswordRequest): Response<RegularResponse>

    @POST("api/recoveryPassword")
    suspend fun postRecoveryPassword(@Body recoveryPasswordRequest: RecoveryPasswordRequest): Response<RegularResponse>
}
