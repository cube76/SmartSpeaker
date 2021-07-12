package com.mqa.smartspeaker.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @field:SerializedName("first_name")
    val first_name: String,

    @field:SerializedName("last_name")
    val last_name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("contact")
    val contact: String,

    @field:SerializedName("message")
    val message: String,
)