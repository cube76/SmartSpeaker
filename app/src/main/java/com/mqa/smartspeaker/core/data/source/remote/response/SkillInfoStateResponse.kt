package com.mqa.smartspeaker.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

class SkillInfoStateResponse (
    @field:SerializedName("show")
    val show: Boolean,
)