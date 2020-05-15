package com.evan.bazar.data.network.post

import com.google.gson.annotations.SerializedName

data class CustomerOrderItem (
    @SerializedName("Id")
    var Id: Int?
)