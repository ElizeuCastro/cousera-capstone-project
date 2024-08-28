package com.example.littlelemon.data.reporitory

import android.content.Context
import com.example.littlelemon.data.local.LittleLemonDatabase
import com.example.littlelemon.data.local.Menu
import com.example.littlelemon.data.local.MenuDAO
import com.example.littlelemon.data.remote.NetworkAPI
import com.example.littlelemon.data.remote.toMenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MenuRepository private constructor(
    private val networkAPI: NetworkAPI,
    private val menuDAO: MenuDAO
) {

    fun fetchMenu(): Flow<List<Menu>> = flow {
        menuDAO.run {
            if (isEmpty()) {
                networkAPI.fetchMenu().toMenu().also {
                    insertAll(it)
                }
            }
            emit(getAll())
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var instance: MenuRepository? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            MenuRepository(
                networkAPI = NetworkAPI(),
                menuDAO = LittleLemonDatabase.getDatabase(context).menuDao()
            ).also {
                instance = it
            }
        }
    }
}