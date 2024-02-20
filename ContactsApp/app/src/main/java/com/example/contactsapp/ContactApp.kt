package com.example.contactsapp

import android.app.Application
import com.example.contactsapp.repository.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ContactApp: Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    //private val database by lazy { SearchDatabase.getDatabase(this, applicationScope) }
    val contactRepository by lazy { ContactRepository() }
}