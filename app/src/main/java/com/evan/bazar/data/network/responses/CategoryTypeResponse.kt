package com.evan.bazar.data.network.responses

import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.data.db.entities.ShopType
import com.google.gson.annotations.SerializedName

data class CategoryTypeResponse (
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: MutableList<CategoryType>?

)