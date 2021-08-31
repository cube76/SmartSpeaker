package com.mqa.smartspeaker.core.data

//import com.dicoding.tourismapp.core.data.source.local.LocalDataSource
import com.mqa.smartspeaker.core.data.source.remote.RemoteDataSource
import com.mqa.smartspeaker.core.data.source.remote.network.ApiResponse
import com.mqa.smartspeaker.core.data.source.remote.request.LoginRequest
import com.mqa.smartspeaker.core.data.source.remote.request.RecoveryPasswordRequest
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

//    override fun getAllTourism(): Flow<Resource<List<Tourism>>> =
//        object : NetworkBoundResource<List<Tourism>, List<TourismResponse>>() {
//            override fun loadFromDB(): Flow<List<Tourism>> {
//                return localDataSource.getAllTourism().map {
//                    DataMapper.mapEntitiesToDomain(it)
//                }
//            }
//
//            override fun shouldFetch(data: List<Tourism>?): Boolean =
////                data == null || data.isEmpty()
//                true // ganti dengan true jika ingin selalu mengambil data dari internet
//
//            override suspend fun createCall(): Flow<ApiResponse<List<TourismResponse>>> =
//                remoteDataSource.getAllTourism()
//
//            override suspend fun saveCallResult(data: List<TourismResponse>) {
//                val tourismList = DataMapper.mapResponsesToEntities(data)
//                localDataSource.insertTourism(tourismList)
//            }
//        }.asFlow()

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

