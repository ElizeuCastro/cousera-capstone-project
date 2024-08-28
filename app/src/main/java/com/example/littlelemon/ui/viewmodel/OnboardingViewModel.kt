package com.example.littlelemon.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.littlelemon.data.reporitory.ProfileRepository
import com.example.littlelemon.data.reporitory.SectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val profileRepository: ProfileRepository,
    private val sectionRepository: SectionRepository
) : ViewModel() {

    fun save(firstName: String, lastName: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.save(firstName, lastName, email)
            sectionRepository.setUserLoggedIn()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as Application)
                OnboardingViewModel(
                    profileRepository = ProfileRepository.getInstance(application),
                    sectionRepository = SectionRepository.getInstance(application)
                )
            }
        }
    }
}