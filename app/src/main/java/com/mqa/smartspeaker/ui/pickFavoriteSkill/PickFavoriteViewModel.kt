package com.mqa.smartspeaker.ui.pickFavoriteSkill

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.SetSkillFavorite
import com.mqa.smartspeaker.core.data.source.remote.request.SetSkillInfo
import com.mqa.smartspeaker.core.data.source.remote.response.RegularResponse
import com.mqa.smartspeaker.core.data.source.remote.response.SkillInfoStateResponse
import com.mqa.smartspeaker.core.data.source.remote.response.Skills
import com.mqa.smartspeaker.core.domain.usecase.SmartSpeakerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickFavoriteViewModel @Inject constructor(private val smartSpeakerUseCase: SmartSpeakerUseCase) : ViewModel(){
    lateinit var getListSkillCategory: LiveData<Resource<List<Skills>>>
    lateinit var setSkillFavorite: LiveData<Resource<RegularResponse>>

    fun getListSkillAsisten(authHeader:String) = viewModelScope.launch {
        getListSkillCategory = smartSpeakerUseCase.getListSkillCategory(authHeader, "Asisten Harian")
            .onStart {
                emit(Resource.Loading())
            }
            .catch { exception -> Resource.Error(exception.toString(), null) }
            .asLiveData()
    }
    fun getListSkillKonten(authHeader:String) = viewModelScope.launch {
        getListSkillCategory = smartSpeakerUseCase.getListSkillCategory(authHeader, "Konten Edukasi dan Berita")
            .onStart {
                emit(Resource.Loading())
            }
            .catch { exception -> Resource.Error(exception.toString(), null) }
            .asLiveData()
    }
    fun getListSkillIslami(authHeader:String) = viewModelScope.launch {
        getListSkillCategory = smartSpeakerUseCase.getListSkillCategory(authHeader, "islami")
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