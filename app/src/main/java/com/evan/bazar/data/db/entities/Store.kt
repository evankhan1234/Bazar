package com.evan.bazar.data.db.entities

import com.google.gson.annotations.SerializedName

class Store (
    @SerializedName("Product")
    var Product: Int?,
    @SerializedName("Supplier")
    var Supplier: Int?,
    @SerializedName("Purchase")
    var Purchase: Int?,
    @SerializedName("Category")
    var Category: Int?
)