package com.evan.bazar.data.network.responses

import com.evan.bazar.data.db.entities.User


data class AuthResponse(
    val isSuccessful : Boolean?,
    val message: String?,
    val user: User?
)