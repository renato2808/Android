package com.example.contactsapp.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.repository.ContactRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ContactViewModel (private val contactRepository: ContactRepository, app: Application) : ViewModel() {
    val contacts = contactRepository.contactsLiveData

    fun fetchContacts(maxPage: Int) = viewModelScope.launch {
        contactRepository.fetchContacts(maxPage)
    }

    suspend fun getBitmapFromURL(url: String?): Bitmap? {
        return contactRepository.getBitmapFromURL(url)
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