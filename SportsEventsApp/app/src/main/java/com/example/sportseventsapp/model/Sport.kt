package com.example.sportseventsapp.model

import com.google.gson.annotations.SerializedName

data class Sport(
    @SerializedName("i") val id: String = "",
    @SerializedName("d") val name: String = "",
    @SerializedName("e") val sportEvents: List<SportEvent> = emptyList()
)