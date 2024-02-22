package com.example.contactsapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.contactsapp.api.ContactListApi
import com.example.contactsapp.data.ContactDao
import com.example.contactsapp.model.Contact
import com.example.contactsapp.model.ContactData
import com.example.contactsapp.model.ContactListResponse
import com.example.contactsapp.model.Location
import com.example.contactsapp.model.Login
import com.example.contactsapp.model.Name
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactRepository(private val contactDao: ContactDao, val app: Application) {

    companion object {
        private const val TAG = "ContactRepository"
    }

    private val contacts = MutableLiveData<List<Contact>?>()
    val contactsLiveData: MutableLiveData<List<Contact>?> = contacts
    private val contactListApi = RetrofitClient.retrofit.create(ContactListApi::class.java)

    suspend fun fetchContacts(maxPage: Int = 1) {
        var page = 1
        var contactsLoading = true
        val allContacts: MutableList<Contact> = mutableListOf()
        withContext(Dispatchers.IO) {
            while (contactsLoading && page <= maxPage) {
                try {
                    val response = contactListApi.getContacts("lydia", 10, page).execute()

                    if (response.isSuccessful) {
                        val contactsResponse = response.body()?.results ?: emptyList()
                        if (contactsResponse.isEmpty()) {
                            contactsLoading = false
                        } else {
                            allContacts.addAll(contactsResponse)
                            page++
                        }
                    } else {
                        Log.e(TAG, "Failed to fetch contacts from server!")
                        contactsLoading = false
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to call server api!", e.cause)
                    contactsLoading = false
                }
            }
            if (allContacts.isEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val contactData = contactDao.getAllContacts().map {
                        Contact.fromJson(it.data)
                    }
                    contacts.postValue(formatContactList(contactData))
                }
            } else {
                contacts.postValue(formatContactList(allContacts))
                CoroutineScope(Dispatchers.IO).launch {
                    contactDao.deleteAll()
                    allContacts.forEach {
                        contactDao.insertContact(ContactData(data = it.toJson()))
                    }
                }
            }
        }
    }

    private fun formatContactList(contactList: List<Contact>): List<Contact> {
        val formattedContactList = mutableListOf<Contact>()

        contactList.forEach { contact ->
            val formattedContact = Contact(
                gender = contact.gender.replaceFirstChar { it.uppercase() },
                name = Name(
                    title = contact.name.title.replaceFirstChar { it.uppercase() },
                    first = contact.name.first.replaceFirstChar { it.uppercase() },
                    last = contact.name.last.replaceFirstChar { it.uppercase() }
                ),
                location = Location(
                    city = contact.location.city.replaceFirstChar { it.uppercase() },
                    state = contact.location.state.replaceFirstChar { it.uppercase() },
                    street = contact.location.street.split(' ').joinToString(separator = " ") { word ->
                        word.replaceFirstChar {
                            it.uppercase()
                        }
                    },
                    postcode = contact.location.postcode
                ),
                email = contact.email,
                login = Login(
                    username = contact.login.username,
                    password = contact.login.password,
                    salt = contact.login.salt,
                    md5 = contact.login.md5,
                    sha1 = contact.login.sha1,
                    sha256 = contact.login.sha256
                ),
                registered = contact.registered,
                dob = contact.dob,
                phone = contact.phone,
                cell = contact.cell,
                id = contact.id,
                picture = contact.picture,
                nat = contact.nat
            )
            formattedContactList.add(formattedContact)
        }

        return formattedContactList
    }
}