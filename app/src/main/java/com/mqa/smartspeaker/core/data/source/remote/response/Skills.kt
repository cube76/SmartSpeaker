package com.mqa.smartspeaker.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class Skills(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("image")
    val image: String,
)
