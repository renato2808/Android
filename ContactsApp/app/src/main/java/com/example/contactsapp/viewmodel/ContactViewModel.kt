package com.example.contactsapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.repository.ContactRepository
import java.lang.IllegalArgumentException

class ContactViewModel (private val contactRepository: ContactRepository, app: Application) : ViewModel() {
    val contacts = contactRepository.contactsLiveData

    fun fetchContacts() {
        contactRepository.fetchContacts()
    }
}

class ContactViewModelFactory(
    private val repository: ContactRepository,
    private val app: Application
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ContactViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(repository, app) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}