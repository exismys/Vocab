package com.example.vocab.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val word: String,
    val pos: String,
    val meaning: String,
    val example: String
)
