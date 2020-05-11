package com.evan.bazar.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.bazar.data.repositories.UserRepository
import com.evan.bazar.ui.auth.AuthViewModel



class AuthViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }
}