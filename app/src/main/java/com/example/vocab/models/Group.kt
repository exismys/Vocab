package com.example.vocab.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class Group(
    @PrimaryKey
    var listName: String,
    var words: List<String>
)