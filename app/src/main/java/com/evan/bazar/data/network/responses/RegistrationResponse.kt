package com.evan.bazar.data.network.responses

data class RegistrationResponse (
    val success : Boolean?,
    val message: String?,
    val messageForShop: String?,
    val status: Int?
)