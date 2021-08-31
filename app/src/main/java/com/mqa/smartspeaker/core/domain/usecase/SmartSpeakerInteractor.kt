package com.mqa.smartspeaker.core.domain.usecase

import com.mqa.smartspeaker.core.data.source.remote.request.LoginRequest
import com.mqa.smartspeaker.core.data.source.remote.request.RecoveryPasswordRequest
import com.mqa.smartspeaker.core.data.source.remote.request.RegisterRequest
import com.mqa.smartspeaker.core.domain.repository.ISmartSpeakerRepository
import javax.inject.Inject

class SmartSpeakerInteractor @Inject constructor(private val ssRepository: ISmartSpeakerRepository):
    SmartSpeakerUseCase {

//    override fun getAllTourism() = ssRepository.getAllTourism()

    override suspend fun postRegister(registerRequest: RegisterRequest) = ssRepository.postRegister(registerRequest)
    override suspend fun getVerifyEmail(email:String,verificationCode: Int) = ssRepository.getVerifyEmail(email,verificationCode)
    override suspend fun getUser(authHeader:String) = ssRepository.getUser(authHeader)
    override suspend fun getLogin(loginRequest: LoginRequest) = ssRepository.getLogin(loginRequest)
    override suspend fun postForgetPassword(email: RecoveryPasswordRequest) = ssRepository.postForgetPassword(email)
    override suspend fun postCheckForgetPasswordCode(recoveryPasswordRequest: RecoveryPasswordRequest) = ssRepository.postCheckForgetPasswordCode(recoveryPasswordRequest)
    override suspend fun postRecoveryPassword(recoveryPasswordRequest: RecoveryPasswordRequest) = ssRepository.postRecoveryPassword(recoveryPasswordRequest)
//    override fun getFavoriteTourism() = ssRepository.getFavoriteTourism()
//
//    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) = ssRepository.setFavoriteTourism(tourism, state)

}