package com.example.vocab.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "group")
data class Group(
    @PrimaryKey
    val listName: String,
    val words: ArrayList<String>
)