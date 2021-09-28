package com.mqa.smartspeaker.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AvatarResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("image")
    val image: String,
)
