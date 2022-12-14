package com.example.vocab.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word")
data class Word(
    @PrimaryKey
    val word: String,
    val wordDetailJson: String
)
