package com.example.contactsapp.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.contactsapp.api.ContactListApi
import com.example.contactsapp.data.ContactDao
import com.example.contactsapp.model.Contact
import com.example.contactsapp.data.ContactData
import com.example.contactsapp.model.Location
import com.example.contactsapp.model.Login
import com.example.contactsapp.model.Name
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class ContactRepository(
    private val contactDao: ContactDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    companion object {
        private const val TAG = "ContactRepository"
        private const val PAGE_SIZE = 10
    }

    private val contacts = MutableLiveData<List<Contact>?>()
    val contactsLiveData: MutableLiveData<List<Contact>?> = contacts
    private val contactListApi = RetrofitClient.retrofit.create(ContactListApi::class.java)

    suspend fun fetchContacts(maxPage: Int) {
        var contactsLoading = true
        //var allContacts: MutableList<Contact>

        withContext(dispatcher) {
            //Getting contacts from database
            val allContacts = contactDao.getAllContacts().map {
                Contact.fromJson(it.data)
            }.toMutableList()
            val lastPageLoaded = allContacts.size / PAGE_SIZE

            if (allContacts.isNotEmpty() && maxPage <= lastPageLoaded) {
                contacts.postValue(formatContactList(allContacts))
            } else {
                var page = lastPageLoaded + 1
                while (contactsLoading && page <= maxPage) {
                    contactListApi.getContacts("lydia", PAGE_SIZE, page).runCatching {
                        execute()
                    }.onFailure {
                        Log.e(TAG, "Failed to call server api!")
                        contactsLoading = false
                    }.onSuccess { response ->
                        val code = response.code()
                        val error = response.errorBody()?.string()
                        if (error != null) {
                            Log.d(TAG, "Fetch contacts $code response with error $error")
                        } else {
                            Log.d(TAG, "Fetch contacts $code response.")
                        }
                    }.mapCatching {
                        it.body()
                    }.mapCatching { response ->
                        val contactsResponse = response?.results ?: emptyList()
                        if (contactsResponse.isEmpty()) {
                            contactsLoading = false
                        } else {
                            allContacts.addAll(contactsResponse)
                            page++
                        }
                    }.onSuccess {
                        Log.d(TAG, "Contacts retrieved successfully: $it")
                    }.onFailure {
                        Log.d(TAG, "Failed fetch contacts from server: ${it.printStackTrace()}")
                        contactsLoading = false
                    }
                }
                contacts.postValue(formatContactList(allContacts))

                // Updating database
                contactDao.deleteAll()
                allContacts.forEach {
                    contactDao.insertContact(ContactData(data = it.toJson()))
                }
            }
        }
    }

    suspend fun getBitmapFromURL(src: String?): Bitmap? {
        return withContext(dispatcher) {
            runCatching {
                val connection = URL(src).openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input = connection.inputStream
                BitmapFactory.decodeStream(input)
            }.onFailure { error ->
                Log.e(TAG, "Error downloading image", error)
            }.onSuccess {
                Log.e(TAG, "Bitmap returned  with ${it.byteCount} bytes")
            }.getOrNull()
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