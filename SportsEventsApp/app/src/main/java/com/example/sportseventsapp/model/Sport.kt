package com.example.sportseventsapp.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Sport(
    @SerializedName("i") val id: String = "",
    @SerializedName("d") val name: String = "",
    @SerializedName("e") val sportEvents: List<SportEvent> = emptyList()
) : Serializable {

    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromJson(json: String): Sport {
            val gson = Gson()
            return gson.fromJson(json, Sport::class.java)
        }
    }
}