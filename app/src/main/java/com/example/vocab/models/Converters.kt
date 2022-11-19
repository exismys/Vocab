package com.example.vocab.models

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromArrayList(value: ArrayList<String>) : String {
        return value.joinToString(",", "")
    }

    @TypeConverter
    fun fromString(value: String) : ArrayList<String> {
        return value.split("") as ArrayList<String>
    }
}