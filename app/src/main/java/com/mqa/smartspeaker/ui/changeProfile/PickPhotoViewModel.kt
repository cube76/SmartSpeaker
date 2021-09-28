package com.mqa.smartspeaker.ui.changeProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.UpdateProfileRequest
import com.mqa.smartspeaker.core.data.source.remote.response.AvatarResponse
import com.mqa.smartspeaker.core.data.source.remote.response.RegularResponse
import com.mqa.smartspeaker.core.domain.usecase.SmartSpeakerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickPhotoViewModel @Inject constructor(private val smartSpeakerUseCase: SmartSpeakerUseCase) : ViewModel() {

    lateinit var getAvatarList: LiveData<Resource<List<AvatarResponse>>>

    fun getAvatarList(authHeader:String) = viewModelScope.launch {
        getAvatarList = smartSpeakerUseCase.getAvatarList(authHeader)
            .onStart {
                emit(Resource.Loading())
            }
            .catch { exception -> Resource.Error(exception.toString(), null) }
            .asLiveData()
    }
}