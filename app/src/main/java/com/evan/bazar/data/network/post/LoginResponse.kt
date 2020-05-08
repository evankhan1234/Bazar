package com.evan.bazar.data.network.post

import com.evan.bazar.data.db.entities.Shop
import com.evan.bazar.data.db.entities.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("jwt")
    val jwt: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: User?,
    @SerializedName("shopId")
    val shopId: Shop?
)