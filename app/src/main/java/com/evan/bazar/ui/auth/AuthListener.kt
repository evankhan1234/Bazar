package com.evan.bazar.ui.auth

import com.evan.bazar.data.db.entities.User


interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}