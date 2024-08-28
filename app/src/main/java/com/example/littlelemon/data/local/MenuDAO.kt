package com.example.littlelemon.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MenuDAO {
    @Query("SELECT * FROM menu_items")
    suspend fun getAll(): List<Menu>

    @Insert
    suspend fun insertAll(menus: List<Menu>)

    @Query("SELECT (SELECT COUNT(*) FROM menu_items) == 0")
    suspend fun isEmpty(): Boolean
}