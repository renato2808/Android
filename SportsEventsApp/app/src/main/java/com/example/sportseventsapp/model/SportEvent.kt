package com.example.sportseventsapp.model

import com.google.gson.annotations.SerializedName

data class SportEvent(
    @SerializedName("i") val id: String = "",
    @SerializedName("d") val name: String = "",
    @SerializedName("sh") val nameSh: String = "",
    @SerializedName("si") val sportId: String = "",
    @SerializedName("tt") val startTime: Int = 0
)