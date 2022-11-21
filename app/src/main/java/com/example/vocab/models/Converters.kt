package com.example.vocab.models

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromMutableList(value: MutableList<String>) : String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun fromString(value: String) : MutableList<String> {
        return value.split(",") as MutableList<String>
    }
}