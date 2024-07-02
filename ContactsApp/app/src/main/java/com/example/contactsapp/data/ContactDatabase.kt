package com.example.contactsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ContactData::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun getContactDao() : ContactDao

    companion object {

        @Volatile
        private var INSTANCE : ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}