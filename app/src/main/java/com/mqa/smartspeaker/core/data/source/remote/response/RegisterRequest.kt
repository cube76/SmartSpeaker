package com.mqa.smartspeaker.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterRequest (
    val first_name: String,

    val last_name: String,

    val email: String,

    val password: String,

    val password_confirmation: String,

    val contact: String,
)