package com.mqa.smartspeaker.core.domain.repository

import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.domain.model.Tourism
import kotlinx.coroutines.flow.Flow

interface ITourismRepository {

    fun getAllTourism(): Flow<Resource<List<Tourism>>>

    fun getFavoriteTourism(): Flow<List<Tourism>>

    fun setFavoriteTourism(tourism: Tourism, state: Boolean)

}