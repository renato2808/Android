package com.example.contactsapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.contactsapp.model.ApiResponse
import com.example.contactsapp.model.Contact
import com.example.contactsapp.api.ContactsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactRepository {

    companion object {
        private const val TAG = "ContactRepository"
    }

    private val contacts = MutableLiveData<List<Contact>?>()
    val contactsLiveData: MutableLiveData<List<Contact>?> = contacts
    private val contactsApi = RetrofitClient.retrofit.create(ContactsApi::class.java)

    fun fetchContacts(){
        val call = contactsApi.getContacts("lydia", 10, 1)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val contactsResponse = response.body()?.results ?: emptyList()
                    contacts.postValue(contactsResponse)
                } else {
                    Log.e(TAG, "Failed to fetch contacts!")
                    contacts.postValue(null)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch contacts!")
            }
        })
    }
}