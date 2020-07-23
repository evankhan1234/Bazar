package com.evan.bazar.data.network.responses

import com.google.gson.annotations.SerializedName

class DeliveryAmountResponses (
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: Double?
)