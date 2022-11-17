package com.example.vocab.models

import androidx.room.Database

@Database(entities = [Word::class], version = 1)
abstract class VocabDatabase {
    abstract fun wordDAO(): WordDAO
}