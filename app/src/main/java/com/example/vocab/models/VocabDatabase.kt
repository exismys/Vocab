package com.example.vocab.models

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Word::class, Group::class], version = 1)
@TypeConverters(Converters::class)
abstract class VocabDatabase: RoomDatabase() {
    abstract fun wordDAO(): WordDAO
    abstract fun groupDAO(): GroupDAO
}