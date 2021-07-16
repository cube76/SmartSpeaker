package com.mqa.smartspeaker.core.data.source.remote.request

data class RecoveryPasswordRequest (
    val email: String,
    val code: String,
    val password: String,
    val password_confirmation: String,
)