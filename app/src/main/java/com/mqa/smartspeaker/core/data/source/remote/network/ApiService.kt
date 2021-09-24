package com.mqa.smartspeaker.core.data.source.remote.network

import com.mqa.smartspeaker.core.data.source.remote.request.*
import com.mqa.smartspeaker.core.data.source.remote.request.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.response.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("list")
    suspend fun getList(): ListTourismResponse

    @GET("api/skillsList")
    suspend fun getListSkill(@Header("Authorization") authHeader:String): Response<List<Skills>>

    @GET("api/skillsList/{category}")
    suspend fun getListSkillCategory(@Header("Authorization") authHeader:String, @Path("category") email: String): Response<List<Skills>>

    @GET("api/skillsFavourite")
    suspend fun getListSkillFavourite(@Header("Authorization") authHeader:String): Response<List<Skills>>

    @GET("api/user")
    suspend fun getUser(@Header("Authorization") authHeader:String): Response<User>

    @POST("api/skillInfoState")
    suspend fun getSkillInfoState(@Header("Authorization") authHeader:String, @Body requestBody: SkillInfoState): Response<SkillInfoStateResponse>

    @POST("api/setSkillInfoState")
    suspend fun setSkillInfoState(@Header("Authorization") authHeader:String, @Body requestBody: SetSkillInfo): Response<RegularResponse>

    @POST("api/setSkillsFavourite")
    suspend fun setSkillFavorite(@Header("Authorization") authHeader:String, @Body requestBody: SetSkillFavorite): Response<RegularResponse>

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
