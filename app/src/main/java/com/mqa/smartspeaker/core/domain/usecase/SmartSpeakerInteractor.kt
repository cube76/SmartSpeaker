package com.mqa.smartspeaker.core.domain.usecase

import com.mqa.smartspeaker.core.data.source.remote.request.LoginRequest
import com.mqa.smartspeaker.core.data.source.remote.request.RecoveryPasswordRequest
import com.mqa.smartspeaker.core.data.source.remote.request.RegisterRequest
import com.mqa.smartspeaker.core.domain.repository.ISmartSpeakerRepository
import javax.inject.Inject

class SmartSpeakerInteractor @Inject constructor(private val tourismRepository: ISmartSpeakerRepository):
    SmartSpeakerUseCase {

//    override fun getAllTourism() = tourismRepository.getAllTourism()

    override suspend fun postRegister(registerRequest: RegisterRequest) = tourismRepository.postRegister(registerRequest)
    override suspend fun getVerifyEmail(email:String,verificationCode: Int) = tourismRepository.getVerifyEmail(email,verificationCode)
    override suspend fun getLogin(loginRequest: LoginRequest) = tourismRepository.getLogin(loginRequest)
    override suspend fun postForgetPassword(email: RecoveryPasswordRequest) = tourismRepository.postForgetPassword(email)
    override suspend fun postCheckForgetPasswordCode(recoveryPasswordRequest: RecoveryPasswordRequest) = tourismRepository.postCheckForgetPasswordCode(recoveryPasswordRequest)
    override suspend fun postRecoveryPassword(recoveryPasswordRequest: RecoveryPasswordRequest) = tourismRepository.postRecoveryPassword(recoveryPasswordRequest)
//    override fun getFavoriteTourism() = tourismRepository.getFavoriteTourism()
//
//    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) = tourismRepository.setFavoriteTourism(tourism, state)

}