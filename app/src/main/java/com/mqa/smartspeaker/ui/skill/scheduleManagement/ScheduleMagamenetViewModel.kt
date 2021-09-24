package com.mqa.smartspeaker.ui.skill.scheduleManagement

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.SkillInfoState
import com.mqa.smartspeaker.core.data.source.remote.response.SkillInfoStateResponse
import com.mqa.smartspeaker.core.domain.usecase.SmartSpeakerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleMagamenetViewModel @Inject constructor(private val smartSpeakerUseCase: SmartSpeakerUseCase) : ViewModel(){
    lateinit var getSkillInfoState: LiveData<Resource<SkillInfoStateResponse>>

    fun getSkillInfoState(authHeader:String, skillId: SkillInfoState) = viewModelScope.launch {
        getSkillInfoState = smartSpeakerUseCase.getSkillInfoState(authHeader, skillId)
            .onStart {
                emit(Resource.Loading())
            }
            .catch { exception -> Resource.Error(exception.toString(), null) }
            .asLiveData()
    }
}