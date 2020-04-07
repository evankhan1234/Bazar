package com.evan.bazar.interfaces

interface Listener {
    fun Success(result: String)
    fun Failure(result: String)
}