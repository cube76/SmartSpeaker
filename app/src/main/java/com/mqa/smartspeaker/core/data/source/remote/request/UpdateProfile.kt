package com.mqa.smartspeaker.core.data.source.remote.request

data class UpdateProfileRequest(
    val id: Int,

    val first_name: String,

    val last_name: String,

    val profile_image: String?,
)
