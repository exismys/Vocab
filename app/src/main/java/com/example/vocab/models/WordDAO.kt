package com.example.vocab.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDAO {
    @Insert
    suspend fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Query("Select * from word")
    fun getAllWords(): LiveData<List<Word>>

    @Query("Select * from word where word = :word limit 1")
    fun getWord(word: String): Word

    @Query("Select * from word where word in (:words)")
    fun getAllWords(words: List<String>): LiveData<List<Word>>

    @Query("Delete from word where word = :word")
    suspend fun deleteByWord(word: String)
}