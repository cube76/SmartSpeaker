package com.mqa.smartspeaker.core.domain.usecase

import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.*
import com.mqa.smartspeaker.core.data.source.remote.request.RegisterRequest
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
    suspend fun getAvatarList(authHeader:String): Flow<Resource<List<AvatarResponse>>>
    suspend fun getSkillInfoState(authHeader:String, skillId: SkillInfoState): Flow<Resource<SkillInfoStateResponse>>
    suspend fun setSkillInfoState(authHeader:String, skill: SetSkillInfo): Flow<Resource<RegularResponse>>
    suspend fun setSkillFavorite(authHeader:String, skill: SetSkillFavorite): Flow<Resource<RegularResponse>>
    suspend fun getListSkill(authHeader:String): Flow<Resource<List<Skills>>>
    suspend fun getListSkillFavourite(authHeader:String): Flow<Resource<List<Skills>>>
    suspend fun getListSkillCategory(authHeader:String, category: String): Flow<Resource<List<Skills>>>
    suspend fun postForgetPassword(email:RecoveryPasswordRequest): Flow<Resource<RegularResponse>>
    suspend fun postCheckForgetPasswordCode(recoveryPasswordRequest: RecoveryPasswordRequest): Flow<Resource<RegularResponse>>
    suspend fun postRecoveryPassword(recoveryPasswordRequest: RecoveryPasswordRequest): Flow<Resource<RegularResponse>>
}