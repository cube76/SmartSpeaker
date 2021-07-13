package com.mqa.smartspeaker.core.domain.usecase

import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterResponse
import com.mqa.smartspeaker.core.data.source.remote.response.VerifyEmailResponse
import com.mqa.smartspeaker.core.domain.model.Tourism
import kotlinx.coroutines.flow.Flow

interface SmartSpeakerUseCase {
//    fun getAllTourism(): Flow<Resource<List<Tourism>>>
//    fun getFavoriteTourism(): Flow<List<Tourism>>
//    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
    suspend fun postRegister(registerRequest: RegisterRequest): Flow<Resource<RegisterResponse>>
    suspend fun getVerifyEmail(email:String,verificationCode: Int): Flow<Resource<VerifyEmailResponse>>
}