package com.mqa.smartspeaker.ui.home

import androidx.lifecycle.*
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.request.SetSkillFavorite
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterResponse
import com.mqa.smartspeaker.core.data.source.remote.response.RegularResponse
import com.mqa.smartspeaker.core.data.source.remote.response.Skills
import com.mqa.smartspeaker.core.domain.usecase.SmartSpeakerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val smartSpeakerUseCase: SmartSpeakerUseCase) : ViewModel() {

    lateinit var getListSkillFavourite: LiveData<Resource<List<Skills>>>
    lateinit var setSkillFavorite: LiveData<Resource<RegularResponse>>

    fun getListSkillFavourite(authHeader:String) = viewModelScope.launch {
        getListSkillFavourite = smartSpeakerUseCase.getListSkillFavourite(authHeader)
            .onStart {
                emit(Resource.Loading())
            }
            .catch { exception -> Resource.Error(exception.toString(), null) }
            .asLiveData()
    }

    fun setSkillFavorite(authHeader:String, skill: SetSkillFavorite) = viewModelScope.launch {
        setSkillFavorite = smartSpeakerUseCase.setSkillFavorite(authHeader, skill)
            .onStart {
                emit(Resource.Loading())
            }
            .catch { exception -> Resource.Error(exception.toString(), null) }
            .asLiveData()
    }
}