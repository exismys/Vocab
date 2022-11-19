package com.example.vocab.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GroupDAO {
    @Insert
    suspend fun insert(group: Group)

    @Delete
    suspend fun delete(group: Group)

    @Update
    suspend fun update(group: Group)

    @Query("Select * from groups")
    fun getGroup() : LiveData<List<Group>>

    @Query("UPDATE groups SET listName = :newName where listName = :oldName")
    suspend fun update(oldName: String, newName: String)
}