package com.example.littlelemon.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProfileDAO {

    @Insert
    suspend fun insert(profile: Profile)

    @Query("SELECT * FROM profile")
    suspend fun getAll(): List<Profile>

    @Query("DELETE FROM profile")
    fun deleteAll()
}