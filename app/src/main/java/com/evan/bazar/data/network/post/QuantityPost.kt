package com.evan.bazar.data.network.post

import com.google.gson.annotations.SerializedName

data class QuantityPost (
    @SerializedName("OrderId")
    var OrderId: Int?,
    @SerializedName("Quantity")
    var Quantity: Int?
)