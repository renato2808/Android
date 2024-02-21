//package com.example.contactsapp.model
//
//import androidx.room.TypeConverter
//
//class Converters {
//    @TypeConverter
//    fun fromName(name: Name?): String? {
//        return name?.let {
//            it.title + "," + it.first + "," + it.last
//        }
//    }
//
//    @TypeConverter
//    fun toName(name: String?): Name? {
//        return name?.split(",").let {
//            it?.let {
//                Name(it[0], it[1], it[1])
//            }
//        }
//    }
//
//    @TypeConverter
//    fun fromLocation(location: Location?): String? {
//        return location?.let {
//            it.street + "," + it.city + "," + it.state + "," + it.postcode.toString()
//        }
//    }
//
//    @TypeConverter
//    fun toLocation(fullName: String?): Location? {
//        return fullName?.split(",").let {
//            it?.let {
//                Location(it[0], it[1], it[2], it[3].toInt())
//            }
//        }
//    }
//
//    @TypeConverter
//    fun fromLogin(login: Login?): String? {
//        return login?.let {
//            it.username + "," + it.city + "," + it.state + "," + it.postcode.toString()
//        }
//    }
//
//    @TypeConverter
//    fun toLogin(fullName: String?): Location? {
//        return fullName?.split(",").let {
//            it?.let {
//                Location(it[0], it[1], it[2], it[3].toInt())
//            }
//        }
//    }
//}