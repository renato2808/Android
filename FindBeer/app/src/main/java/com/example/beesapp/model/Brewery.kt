package com.example.beesapp.model

data class Brewery(
    val name: String = "",
    val brewery_type: String? = "",
    val rating: Float = 3.0f,
    val website_url: String? = "",
    val street: String? = ""
)