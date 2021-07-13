package com.mqa.smartspeaker.core.domain.usecase

import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterResponse
import com.mqa.smartspeaker.core.domain.repository.ISmartSpeakerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SmartSpeakerInteractor @Inject constructor(private val tourismRepository: ISmartSpeakerRepository):
    SmartSpeakerUseCase {

//    override fun getAllTourism() = tourismRepository.getAllTourism()

    override suspend fun postRegister(registerRequest: RegisterRequest) = tourismRepository.postRegister(registerRequest)
    override suspend fun getVerifyEmail(email:String,verificationCode: Int) = tourismRepository.getVerifyEmail(email,verificationCode)

//    override fun getFavoriteTourism() = tourismRepository.getFavoriteTourism()
//
//    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) = tourismRepository.setFavoriteTourism(tourism, state)

}