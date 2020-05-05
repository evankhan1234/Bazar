package com.evan.bazar.data.network.post

import com.evan.bazar.data.db.entities.User

data class LoginResponse(
    val status: Int?,
    val success: Boolean?,
    val jwt: String?,
    val message: String?,
    val data: User?
)