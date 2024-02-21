package com.example.contactsapp.model

import com.google.gson.Gson
import java.io.Serializable

data class Contact(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val registered: Long,
    val dob: Long,
    val phone: String,
    val cell: String,
    val id: Id,
    val picture: Picture,
    val nat: String
) : Serializable {
    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromJson(json: String): Contact {
            return Gson().fromJson(json, Contact::class.java)
        }
    }
}