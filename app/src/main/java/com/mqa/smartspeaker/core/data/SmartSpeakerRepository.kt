package com.mqa.smartspeaker.core.data

//import com.dicoding.tourismapp.core.data.source.local.LocalDataSource
import com.mqa.smartspeaker.core.data.source.remote.RemoteDataSource
import com.mqa.smartspeaker.core.data.source.remote.network.ApiResponse
import com.mqa.smartspeaker.core.data.source.remote.request.*
import com.mqa.smartspeaker.core.data.source.remote.request.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.response.*
import com.mqa.smartspeaker.core.domain.repository.ISmartSpeakerRepository
import com.mqa.smartspeaker.core.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmartSpeakerRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
//    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ISmartSpeakerRepository {

    override suspend fun postRegister(registerRequest: RegisterRequest): Flow<Resource<RegisterResponse>> {
        return remoteDataSource.postRegister(registerRequest).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun getSkillInfoState(authHeader:String, skillId: SkillInfoState): Flow<Resource<SkillInfoStateResponse>> {
        return remoteDataSource.getSkillInfoState(authHeader, skillId).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun getSkillFavoriteState(authHeader:String, skillId: SkillInfoState): Flow<Resource<SkillFavoriteStateResponse>> {
        return remoteDataSource.getSkillFavoriteState(authHeader, skillId).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun getAvatarList(authHeader:String): Flow<Resource<List<AvatarResponse>>> {
        return remoteDataSource.getAvatarList(authHeader).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun setSkillInfoState(authHeader:String, skill: SetSkillInfo): Flow<Resource<RegularResponse>> {
        return remoteDataSource.setSkillInfoState(authHeader, skill).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun setSkillFavorite(authHeader:String, skill: SetSkillFavorite): Flow<Resource<RegularResponse>> {
        return remoteDataSource.setSkillFavorite(authHeader, skill).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun postUpdateProfile(authHeader:String, updateProfileRequest: UpdateProfileRequest): Flow<Resource<RegularResponse>> {
        return remoteDataSource.postUpdateProfile(authHeader, updateProfileRequest).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun getVerifyEmail(email: String,verificationCode: Int): Flow<Resource<VerifyEmailResponse>> {
        return remoteDataSource.getVerifyEmail(email,verificationCode).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun getUser(authHeader:String): Flow<Resource<User>> {
        return remoteDataSource.getUser(authHeader).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun getListSkill(authHeader:String): Flow<Resource<List<Skills>>> {
        return remoteDataSource.getListSkill(authHeader).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun getListSkillFavourite(authHeader:String): Flow<Resource<List<Skills>>> {
        return remoteDataSource.getListSkillFavourite(authHeader).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun getListSkillCategory(authHeader:String, category: String): Flow<Resource<List<Skills>>> {
        return remoteDataSource.getListSkillCategory(authHeader, category).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun getLogin(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> {
        return remoteDataSource.getLogin(loginRequest).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun postForgetPassword(email: RecoveryPasswordRequest): Flow<Resource<RegularResponse>> {
        return remoteDataSource.postForgetPassword(email).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun postCheckForgetPasswordCode(recoveryPasswordRequest: RecoveryPasswordRequest): Flow<Resource<RegularResponse>> {
        return remoteDataSource.postCheckForgetPasswordCode(recoveryPasswordRequest).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

    override suspend fun postRecoveryPassword(recoveryPasswordRequest: RecoveryPasswordRequest): Flow<Resource<RegularResponse>> {
        return remoteDataSource.postRecoveryPassword(recoveryPasswordRequest).map {
            when (it) {
                is ApiResponse.Success -> {
                    Resource.Success(it.data!!)
                }
                is ApiResponse.Empty -> Resource.Error(it.toString())
                is ApiResponse.Error -> Resource.Error(it.message)
            }
        }
    }

//    override fun getFavoriteTourism(): Flow<List<Tourism>> {
//        return localDataSource.getFavoriteTourism().map {
//            DataMapper.mapEntitiesToDomain(it)
//        }
//    }
//
//    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) {
//        val tourismEntity = DataMapper.mapDomainToEntity(tourism)
//        appExecutors.diskIO().execute { localDataSource.setFavoriteTourism(tourismEntity, state) }
//    }
}

