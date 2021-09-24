package com.mqa.smartspeaker.core.domain.usecase

import com.mqa.smartspeaker.core.data.source.remote.request.*
import com.mqa.smartspeaker.core.domain.repository.ISmartSpeakerRepository
import javax.inject.Inject

class SmartSpeakerInteractor @Inject constructor(private val ssRepository: ISmartSpeakerRepository):
    SmartSpeakerUseCase {

//    override fun getAllTourism() = ssRepository.getAllTourism()

    override suspend fun postRegister(registerRequest: RegisterRequest) = ssRepository.postRegister(registerRequest)
    override suspend fun postUpdateProfile(authHeader:String, updateProfileRequest: UpdateProfileRequest) = ssRepository.postUpdateProfile(authHeader,updateProfileRequest)
    override suspend fun getVerifyEmail(email:String,verificationCode: Int) = ssRepository.getVerifyEmail(email,verificationCode)
    override suspend fun getUser(authHeader:String) = ssRepository.getUser(authHeader)
    override suspend fun getSkillInfoState(authHeader:String, skillId: SkillInfoState) = ssRepository.getSkillInfoState(authHeader, skillId)
    override suspend fun setSkillInfoState(authHeader:String, skill: SetSkillInfo) = ssRepository.setSkillInfoState(authHeader, skill)
    override suspend fun setSkillFavorite(authHeader:String, skill: SetSkillFavorite) = ssRepository.setSkillFavorite(authHeader, skill)
    override suspend fun getListSkill(authHeader:String) = ssRepository.getListSkill(authHeader)
    override suspend fun getListSkillFavourite(authHeader:String) = ssRepository.getListSkillFavourite(authHeader)
    override suspend fun getListSkillCategory(authHeader:String, category: String) = ssRepository.getListSkillCategory(authHeader, category)
    override suspend fun getLogin(loginRequest: LoginRequest) = ssRepository.getLogin(loginRequest)
    override suspend fun postForgetPassword(email: RecoveryPasswordRequest) = ssRepository.postForgetPassword(email)
    override suspend fun postCheckForgetPasswordCode(recoveryPasswordRequest: RecoveryPasswordRequest) = ssRepository.postCheckForgetPasswordCode(recoveryPasswordRequest)
    override suspend fun postRecoveryPassword(recoveryPasswordRequest: RecoveryPasswordRequest) = ssRepository.postRecoveryPassword(recoveryPasswordRequest)
//    override fun getFavoriteTourism() = ssRepository.getFavoriteTourism()
//
//    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) = ssRepository.setFavoriteTourism(tourism, state)

}