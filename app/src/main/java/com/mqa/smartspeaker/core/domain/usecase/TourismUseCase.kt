package com.mqa.smartspeaker.core.domain.usecase

import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.domain.model.Tourism
import kotlinx.coroutines.flow.Flow

interface TourismUseCase {
    fun getAllTourism(): Flow<Resource<List<Tourism>>>
    fun getFavoriteTourism(): Flow<List<Tourism>>
    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
}