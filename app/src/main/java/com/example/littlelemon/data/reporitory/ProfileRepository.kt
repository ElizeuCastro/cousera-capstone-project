package com.example.littlelemon.data.reporitory

import android.content.Context
import com.example.littlelemon.data.local.LittleLemonDatabase
import com.example.littlelemon.data.local.Profile
import com.example.littlelemon.data.local.ProfileDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProfileRepository private constructor(
    private val profileDAO: ProfileDAO
) {

    fun get() = flow {
        emit(profileDAO.getAll().firstOrNull())
    }.flowOn(Dispatchers.IO)

    suspend fun save(firstName: String, lastName: String, email: String) {
        profileDAO.insert(
            Profile(
                firstName = firstName, lastName = lastName, email = email
            )
        )
    }

    fun clean() {
        profileDAO.deleteAll()
    }

    companion object {
        @Volatile
        private var instance: ProfileRepository? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            ProfileRepository(
                profileDAO = LittleLemonDatabase.getDatabase(context).profileDao()
            ).also {
                instance = it
            }
        }
    }

}