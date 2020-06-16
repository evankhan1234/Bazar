package com.evan.bazar.ui.home.settings.password

interface IChangePasswordListener {
    fun onStarted()
    fun onEnd()
    fun onUser(message: String)
    fun onFailure(message: String)
}