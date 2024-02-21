package com.example.contactsapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.contactsapp.api.ContactListApi
import com.example.contactsapp.data.ContactDao
import com.example.contactsapp.model.Contact
import com.example.contactsapp.model.ContactData
import com.example.contactsapp.model.ContactListResponse
import com.example.contactsapp.model.Location
import com.example.contactsapp.model.Name
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactRepository(private val contactDao: ContactDao) {

    companion object {
        private const val TAG = "ContactRepository"
    }

    private val contacts = MutableLiveData<List<Contact>?>()
    val contactsLiveData: MutableLiveData<List<Contact>?> = contacts
    private val contactListApi = RetrofitClient.retrofit.create(ContactListApi::class.java)

    suspend fun fetchContacts() {
        withContext(Dispatchers.IO) {
            val call = contactListApi.getContacts("lydia", 100, 1)
            call.enqueue(object : Callback<ContactListResponse> {
                override fun onResponse(call: Call<ContactListResponse>, response: Response<ContactListResponse>) {
                    if (response.isSuccessful) {
                        val contactsResponse = response.body()?.results ?: emptyList()
                        contacts.postValue(formatContactList(contactsResponse))

                        CoroutineScope(Dispatchers.IO).launch {
                            contactDao.deleteAll()
                            contactsResponse.forEach {
                                contactDao.insertContact(ContactData(data = it.toJson()))
                            }
                        }
                    } else {
                        Log.e(TAG, "Failed to fetch contacts from server!")
                        CoroutineScope(Dispatchers.IO).launch {
                            val contactData = contactDao.getAllContacts().map {
                                Contact.fromJson(it.data)
                            }
                            contacts.postValue(formatContactList(contactData))
                        }
                    }
                }

                override fun onFailure(call: Call<ContactListResponse>, t: Throwable) {
                    Log.e(TAG, "Failed to call server api!")
                    CoroutineScope(Dispatchers.IO).launch {
                        val contactData = contactDao.getAllContacts().map {
                            Contact.fromJson(it.data)
                        }
                        contacts.postValue(formatContactList(contactData))
                    }
                }
            })
        }
    }

    fun formatContactList(contactList: List<Contact>): List<Contact> {
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
                login = contact.login,
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