package com.example.contactsapp.model

import java.io.Serializable

data class ContactListResponse(
    val results: List<Contact>
): Serializable