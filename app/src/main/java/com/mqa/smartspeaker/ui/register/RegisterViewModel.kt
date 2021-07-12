package com.mqa.smartspeaker.ui.register

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterResponse
import com.mqa.smartspeaker.core.domain.usecase.TourismUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val smartSpeakerUseCase: TourismUseCase) : ViewModel() {

    lateinit var postRegister: LiveData<Resource<RegisterResponse>>

    fun postRegister(registerRequest: RegisterRequest) = viewModelScope.launch {
        postRegister = smartSpeakerUseCase.postRegister(registerRequest)
            .onStart {
                emit(Resource.Loading())
            }
            .catch { exception -> Resource.Error(exception.toString(), null) }
            .asLiveData()
    }
}