package com.example.contactsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class ContactData(
    @PrimaryKey(autoGenerate = true) val contactId: Long = 0,
    val data: String
)