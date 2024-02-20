package com.example.contactsapp.model

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
) : Serializable