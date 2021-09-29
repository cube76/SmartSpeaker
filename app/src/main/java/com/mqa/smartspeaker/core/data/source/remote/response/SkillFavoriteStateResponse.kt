package com.mqa.smartspeaker.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SkillFavoriteStateResponse(
    @field:SerializedName("favorite")
    val favorite: Boolean,
)
