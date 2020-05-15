package com.evan.bazar.data.network.post

import com.evan.bazar.data.db.entities.CustomerOrder
import com.google.gson.annotations.SerializedName

data class CustomerOrderDetailsStatus (
    @SerializedName("data")
    val data: MutableList<CustomerOrderStatus>?
)