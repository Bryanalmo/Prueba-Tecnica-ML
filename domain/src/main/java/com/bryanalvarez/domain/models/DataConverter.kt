package com.bryanalvarez.domain.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun fromAttributeList(value: List<Attribute>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<Attribute>?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toAttributeList(value: String?): List<Attribute>? {
        val gson = Gson()
        val type = object : TypeToken<List<Attribute>?>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromBoolean(value: Boolean): String {
        val gson = Gson()
        val type = object : TypeToken<Boolean>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toBoolean(value: String): Boolean {
        val gson = Gson()
        val type = object : TypeToken<Boolean>() {}.type
        return gson.fromJson(value, type)
    }
}