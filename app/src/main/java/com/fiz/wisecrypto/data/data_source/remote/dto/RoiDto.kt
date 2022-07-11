package com.fiz.wisecrypto.data.data_source.remote.dto

import com.google.gson.annotations.SerializedName

data class RoiDto(
    @SerializedName("times") val times: Double = 0.0,
    @SerializedName("currency") val currency: String = "",
    @SerializedName("percentage") val percentage: Double = 0.0
)