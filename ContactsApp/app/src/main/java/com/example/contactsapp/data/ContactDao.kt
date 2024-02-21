package com.example.contactsapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.contactsapp.model.ContactData

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact_table")
    fun getAllContacts(): List<ContactData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contacts: ContactData)

    @Query("DELETE FROM contact_table")
    fun deleteAll()
}