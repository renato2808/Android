package com.example.beesapp.model

import kotlin.math.roundToInt
import kotlin.random.Random

data class Brewery(
    val name: String = "",
    val brewery_type: String? = "",
    val rating: Float = (Random.nextDouble(0.0, 5.0)*10).roundToInt()/10f,
    val website_url: String? = "",
    val street: String? = "",
    val city: String? = "",
    val state: String? = ""
)