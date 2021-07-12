package com.mqa.smartspeaker.core.domain.repository

import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterResponse
import com.mqa.smartspeaker.core.domain.model.Tourism
import kotlinx.coroutines.flow.Flow

interface ITourismRepository {

//    fun getAllTourism(): Flow<Resource<List<Tourism>>>

    suspend fun postRegister(registerRequest: RegisterRequest): Flow<Resource<RegisterResponse>>

//    fun getFavoriteTourism(): Flow<List<Tourism>>
//
//    fun setFavoriteTourism(tourism: Tourism, state: Boolean)

}