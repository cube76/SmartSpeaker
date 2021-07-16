package com.mqa.smartspeaker.ui.forgetPassword.inputCode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.RecoveryPasswordRequest
import com.mqa.smartspeaker.core.data.source.remote.response.RegularResponse
import com.mqa.smartspeaker.core.domain.usecase.SmartSpeakerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputCodeViewModel @Inject constructor(private val smartSpeakerUseCase: SmartSpeakerUseCase) :
    ViewModel() {

    lateinit var postCheckForgetPasswordCode: LiveData<Resource<RegularResponse>>
    lateinit var postForgetPassword: LiveData<Resource<RegularResponse>>

    fun postCheckForgetPasswordCode(recoveryPasswordRequest: RecoveryPasswordRequest) =
        viewModelScope.launch {
            postCheckForgetPasswordCode =
                smartSpeakerUseCase.postCheckForgetPasswordCode(recoveryPasswordRequest)
                    .onStart {
                        emit(Resource.Loading())
                    }
                    .catch { exception -> Resource.Error(exception.toString(), null) }
                    .asLiveData()
        }


    fun postForgetPassword(email: RecoveryPasswordRequest) = viewModelScope.launch {
        postForgetPassword = smartSpeakerUseCase.postForgetPassword(email)
            .onStart {
                emit(Resource.Loading())
            }
            .catch { exception -> Resource.Error(exception.toString(), null) }
            .asLiveData()
    }
}