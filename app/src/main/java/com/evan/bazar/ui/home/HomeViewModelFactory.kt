package com.evan.bazar.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.data.repositories.UserRepository
import com.evan.bazar.ui.auth.AuthViewModel

class HomeViewModelFactory (
    private val repository: HomeRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}