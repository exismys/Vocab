package com.example.vocab.models

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1)
abstract class VocabDatabase: RoomDatabase() {
    abstract fun wordDAO(): WordDAO
}