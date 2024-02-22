package com.example.contactsapp.model

import java.io.Serializable

data class Location(
    val street: String,
    val city: String,
    val state: String,
    val postcode: String
): Serializable