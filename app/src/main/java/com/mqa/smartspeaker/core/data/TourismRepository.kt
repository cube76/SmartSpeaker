package com.mqa.smartspeaker.core.data

//import com.dicoding.tourismapp.core.data.source.local.LocalDataSource
import android.util.Log
import com.mqa.smartspeaker.core.data.source.remote.RemoteDataSource
import com.mqa.smartspeaker.core.data.source.remote.network.ApiResponse
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterResponse
import com.mqa.smartspeaker.core.data.source.remote.response.TourismResponse
import com.mqa.smartspeaker.core.domain.model.Tourism
import com.mqa.smartspeaker.core.domain.repository.ITourismRepository
import com.mqa.smartspeaker.core.utils.AppExecutors
import com.mqa.smartspeaker.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TourismRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
//    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ITourismRepository {

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

