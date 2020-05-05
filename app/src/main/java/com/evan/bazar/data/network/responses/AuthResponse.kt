package com.evan.bazar.data.network.responses

import com.evan.bazar.data.db.entities.User
import com.google.gson.annotations.SerializedName


data class AuthResponse(
    @SerializedName("isSuccessful")
    val isSuccessful : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("jwt")
    val jwt: String?,
    @SerializedName("data")
    val data: User?
)