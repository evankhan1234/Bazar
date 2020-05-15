package com.evan.bazar.data.network.post

import com.google.gson.annotations.SerializedName

data class CustomerOrderStatus (
    @SerializedName("OrderId")
    var OrderId: Int?,
    @SerializedName("Status")
    var Status: Int?
)