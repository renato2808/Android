package com.example.contactsapp

import android.app.Application
import com.example.contactsapp.data.ContactDatabase
import com.example.contactsapp.repository.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ContactApp: Application() {

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val contactDatabase by lazy { ContactDatabase.getDatabase(this) }
    val contactRepository by lazy { ContactRepository(contactDatabase.getContactDao()) }
}