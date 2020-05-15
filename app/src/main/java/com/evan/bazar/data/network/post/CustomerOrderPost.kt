package com.evan.bazar.data.network.post

import com.google.gson.annotations.SerializedName

data class CustomerOrderPost (
    @SerializedName("OrderId")
    var OrderId: Int?
)