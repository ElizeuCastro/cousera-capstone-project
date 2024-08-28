package com.example.littlelemon.data.reporitory

import android.content.Context

class SectionRepository private constructor(
    context: Context
) {
    private val userPref =
        context.getSharedPreferences("USER_SECTION", Context.MODE_PRIVATE)

    fun setUserLoggedIn() {
        userPref.edit().putBoolean("USER_LOGGED_IN", true).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return userPref.getBoolean("USER_LOGGED_IN", false)
    }

    fun clean() {
        userPref.edit().putBoolean("USER_LOGGED_IN", false).apply()
    }

    companion object {
        @Volatile
        private var instance: SectionRepository? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            SectionRepository(
                context
            ).also {
                instance = it
            }
        }
    }
}