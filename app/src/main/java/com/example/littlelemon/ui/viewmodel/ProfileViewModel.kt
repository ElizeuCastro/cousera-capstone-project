package com.example.littlelemon.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.littlelemon.data.local.Profile
import com.example.littlelemon.data.reporitory.ProfileRepository
import com.example.littlelemon.data.reporitory.SectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val sectionRepository: SectionRepository
) : ViewModel() {

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile = _profile.asStateFlow()

    init {
        profileRepository.get().onEach {
            _profile.value = it
        }.launchIn(viewModelScope)

    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.clean()
            sectionRepository.clean()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application)
                ProfileViewModel(
                    profileRepository = ProfileRepository.getInstance(application),
                    sectionRepository = SectionRepository.getInstance(application)
                )
            }
        }
    }
}