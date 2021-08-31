package com.mqa.smartspeaker.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("first_name")
    val first_name: String,

    @field:SerializedName("last_name")
    val last_name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("contact")
    val contact: String,

    @field:SerializedName("homeId")
    val homeId: String,

    @field:SerializedName("is_verified")
    val is_verified: Int,
)
