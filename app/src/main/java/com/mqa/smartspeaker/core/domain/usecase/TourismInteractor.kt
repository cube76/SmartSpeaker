package com.mqa.smartspeaker.core.domain.usecase

import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterResponse
import com.mqa.smartspeaker.core.domain.model.Tourism
import com.mqa.smartspeaker.core.domain.repository.ITourismRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TourismInteractor @Inject constructor(private val tourismRepository: ITourismRepository):
    TourismUseCase {

//    override fun getAllTourism() = tourismRepository.getAllTourism()

    override suspend fun postRegister(registerRequest: RegisterRequest) = tourismRepository.postRegister(registerRequest)

//    override fun getFavoriteTourism() = tourismRepository.getFavoriteTourism()
//
//    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) = tourismRepository.setFavoriteTourism(tourism, state)

}