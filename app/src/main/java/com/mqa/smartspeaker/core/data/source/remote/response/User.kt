package com.mqa.smartspeaker.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class User (
    @field:SerializedName("user")
    val user: UserResponse
)