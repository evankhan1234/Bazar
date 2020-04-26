package com.evan.bazar.ui.interfaces

interface SignUpInterface {
    fun onStartProgress()
    fun onEndProgress()
    fun onSignUpSuccess(message: String)
    fun onSignUpFailed(message: String)
}