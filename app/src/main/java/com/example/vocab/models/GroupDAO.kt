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
    fun getAllGroups() : LiveData<List<Group>>

    @Query("Select * from groups where listName = :listName limit 1")
    fun getGroup(listName: String): Group

    @Query("Select * from groups where listName = :listName limit 1")
    fun getGroupLive(listName: String): LiveData<Group>

    @Query("UPDATE groups SET listName = :newName where listName = :oldName")
    suspend fun update(oldName: String, newName: String)

    @Query("Update groups set words = :words where listName = :listName")
    suspend fun update(listName: String, words: MutableList<String>)
}