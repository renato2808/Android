package com.example.contactsapp.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.repository.ContactRepository
import kotlinx.coroutines.launch

class ContactViewModel(private val contactRepository: ContactRepository?) : ViewModel() {
    val contacts = contactRepository?.contactsLiveData

    fun fetchContacts(maxPage: Int) = viewModelScope.launch {
        contactRepository?.fetchContacts(maxPage)
    }

    suspend fun getBitmapFromURL(url: String?): Bitmap? {
        return contactRepository?.getBitmapFromURL(url)
    }
}

class ContactViewModelFactory(
    private val repository: ContactRepository?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(repository) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}