package com.evan.bazar.data.network.post

import com.google.gson.annotations.SerializedName

data class PurchaseUpdatePost (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("ProductName")
    var ProductName: String?,
    @SerializedName("ProductDetails")
    var ProductDetails: String?,
    @SerializedName("PurchaseNo")
    var PurchaseNo: String?,
    @SerializedName("PurchaseDate")
    var PurchaseDate: String?,
    @SerializedName("Stock")
    var Stock: Int?,
    @SerializedName("Item")
    var Item: Int?,
    @SerializedName("Quantity")
    var Quantity: Int?,
    @SerializedName("Rate")
    var Rate: Double?,
    @SerializedName("Discount")
    var Discount: Double?,
    @SerializedName("Total")
    var Total: Double?,
    @SerializedName("GrandTotal")
    var GrandTotal: Double?,
    @SerializedName("UnitId")
    var UnitId: Int?,
    @SerializedName("ShopId")
    var ShopId: Int?,
    @SerializedName("Created")
    var Created: String?,
    @SerializedName("Status")
    var Status: Int?
)