package com.example.littlelemon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Menu::class, Profile::class], version = 1)
abstract class LittleLemonDatabase : RoomDatabase() {

    abstract fun menuDao(): MenuDAO
    abstract fun profileDao(): ProfileDAO

    companion object {
        @Volatile
        private var instance: LittleLemonDatabase? = null

        fun getDatabase(context: Context): LittleLemonDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    LittleLemonDatabase::class.java,
                    "little_lemon_database"
                )
                    .build()
                    .also { instance = it }
            }
        }
    }
}
