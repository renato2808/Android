package com.example.contactsapp.model

import java.io.Serializable

data class ApiResponse(
    val results: List<Contact>
): Serializable