package com.mqa.smartspeaker.core.data.source.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mqa.smartspeaker.core.data.source.remote.network.ApiResponse
import com.mqa.smartspeaker.core.data.source.remote.network.ApiService
import com.mqa.smartspeaker.core.data.source.remote.request.LoginRequest
import com.mqa.smartspeaker.core.data.source.remote.request.RecoveryPasswordRequest
import com.mqa.smartspeaker.core.data.source.remote.request.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.request.UpdateProfileRequest
import com.mqa.smartspeaker.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllTourism(): Flow<ApiResponse<List<TourismResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.places
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.places))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUser(authHeader:String): Flow<ApiResponse<User?>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getUser(authHeader)
                Log.e("respon",""+response.body()?.user)
                if (response.isSuccessful){
                    emit(ApiResponse.Success(response.body()))
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<RegularResponse>() {}.type
                    var errorResponse: RegularResponse = gson.fromJson(response.errorBody()!!.charStream(), type)
                    emit(ApiResponse.Error(errorResponse.message))
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun postUpdateProfile(authHeader:String, request: UpdateProfileRequest): Flow<ApiResponse<RegularResponse?>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.postUpdateProfile(authHeader, request)
//                val dataArray = response.places
                Log.e("respon",""+response)
                if (response.isSuccessful){
                    emit(ApiResponse.Success(response.body()))
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<RegisterResponse>() {}.type
                    var errorResponse: RegisterResponse = gson.fromJson(response.errorBody()!!.charStream(), type)
                    emit(ApiResponse.Error(errorResponse.message))
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun postRegister(registerRequest: RegisterRequest): Flow<ApiResponse<RegisterResponse?>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.postRegister(registerRequest)
//                val dataArray = response.places
                Log.e("respon",""+response)
                if (response.isSuccessful){
                    emit(ApiResponse.Success(response.body()))
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<RegisterResponse>() {}.type
                    var errorResponse: RegisterResponse = gson.fromJson(response.errorBody()!!.charStream(), type)
                    emit(ApiResponse.Error(errorResponse.message))
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getVerifyEmail(email: String,verificationCode: Int): Flow<ApiResponse<VerifyEmailResponse?>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getVerifyEmail(email,verificationCode)
                Log.e("respon",""+response)
                if (response.isSuccessful){
                    emit(ApiResponse.Success(response.body()))
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<RegularResponse>() {}.type
                    var errorResponse: RegularResponse = gson.fromJson(response.errorBody()!!.charStream(), type)
                    emit(ApiResponse.Error(errorResponse.message))
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getLogin(loginRequest: LoginRequest): Flow<ApiResponse<LoginResponse?>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.postLogin(loginRequest)
                Log.e("respon",""+response)
                if (response.isSuccessful){
                    emit(ApiResponse.Success(response.body()))
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<RegularResponse>() {}.type
                    var errorResponse: RegularResponse = gson.fromJson(response.errorBody()!!.charStream(), type)
                    emit(ApiResponse.Error(errorResponse.message))
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun postForgetPassword(email: RecoveryPasswordRequest): Flow<ApiResponse<RegularResponse?>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.postForgetPassword(email)
                Log.e("respon",""+response)
                if (response.isSuccessful){
                    emit(ApiResponse.Success(response.body()))
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<RegularResponse>() {}.type
                    var errorResponse: RegularResponse = gson.fromJson(response.errorBody()!!.charStream(), type)
                    emit(ApiResponse.Error(errorResponse.message))
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun postCheckForgetPasswordCode(recoveryPasswordRequest: RecoveryPasswordRequest): Flow<ApiResponse<RegularResponse?>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.postCheckForgetPasswordCode(recoveryPasswordRequest)
                Log.e("respon",""+response)
                if (response.isSuccessful){
                    emit(ApiResponse.Success(response.body()))
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<RegularResponse>() {}.type
                    var errorResponse: RegularResponse = gson.fromJson(response.errorBody()!!.charStream(), type)
                    emit(ApiResponse.Error(errorResponse.message))
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun postRecoveryPassword(recoveryPasswordRequest: RecoveryPasswordRequest): Flow<ApiResponse<RegularResponse?>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.postRecoveryPassword(recoveryPasswordRequest)
                Log.e("respon",""+response)
                if (response.isSuccessful){
                    emit(ApiResponse.Success(response.body()))
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<RegularResponse>() {}.type
                    var errorResponse: RegularResponse = gson.fromJson(response.errorBody()!!.charStream(), type)
                    emit(ApiResponse.Error(errorResponse.message))
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}

