package com.example.littlelemon.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.littlelemon.data.local.Menu
import com.example.littlelemon.data.reporitory.MenuRepository
import com.example.littlelemon.data.reporitory.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel(
    private val menuRepository: MenuRepository
) : ViewModel() {

    private val _menu = MutableStateFlow<List<Menu>>(emptyList())
    val menu = _menu.asStateFlow()

    init {
        menuRepository.fetchMenu()
            .onEach { _menu.value = it }
            .launchIn(viewModelScope)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application)
                HomeViewModel(
                    menuRepository = MenuRepository.getInstance(application)
                )
            }
        }
    }

}