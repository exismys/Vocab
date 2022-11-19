package com.example.vocab.models

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromArrayList(value: List<String>) : String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun fromString(value: String) : List<String> {
        return value.split(",")
    }
}