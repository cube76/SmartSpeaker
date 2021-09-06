package com.mqa.smartspeaker.core.domain.usecase

import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.LoginRequest
import com.mqa.smartspeaker.core.data.source.remote.request.RecoveryPasswordRequest
import com.mqa.smartspeaker.core.data.source.remote.request.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.request.UpdateProfileRequest
import com.mqa.smartspeaker.core.data.source.remote.response.*
import kotlinx.coroutines.flow.Flow

interface SmartSpeakerUseCase {
//    fun getAllTourism(): Flow<Resource<List<Tourism>>>
//    fun getFavoriteTourism(): Flow<List<Tourism>>
//    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
    suspend fun postRegister(registerRequest: RegisterRequest): Flow<Resource<RegisterResponse>>
    suspend fun postUpdateProfile(authHeader:String,updateProfileRequest: UpdateProfileRequest): Flow<Resource<RegularResponse>>
    suspend fun getVerifyEmail(email:String,verificationCode: Int): Flow<Resource<VerifyEmailResponse>>
    suspend fun getLogin(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>
    suspend fun getUser(authHeader:String): Flow<Resource<User>>
    suspend fun postForgetPassword(email:RecoveryPasswordRequest): Flow<Resource<RegularResponse>>
    suspend fun postCheckForgetPasswordCode(recoveryPasswordRequest: RecoveryPasswordRequest): Flow<Resource<RegularResponse>>
    suspend fun postRecoveryPassword(recoveryPasswordRequest: RecoveryPasswordRequest): Flow<Resource<RegularResponse>>
}