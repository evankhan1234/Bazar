package com.evan.bazar.data.network.post

import com.google.gson.annotations.SerializedName

data class ProductUpdatePost (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("Details")
    var Details: String?,
    @SerializedName("ProductCode")
    var ProductCode: String?,
    @SerializedName("ProductImage")
    var ProductImage: String?,
    @SerializedName("UnitId")
    var UnitId: Int?,
    @SerializedName("SellPrice")
    var SellPrice: Double?,
    @SerializedName("SupplierPrice")
    var SupplierPrice: Double?,
    @SerializedName("SupplierId")
    var SupplierId: Int?,
    @SerializedName("ShopId")
    var ShopId: Int?,
    @SerializedName("Stock")
    var Stock: Int?,
    @SerializedName("Discounts")
    var Discounts: Double?,
    @SerializedName("Created")
    var Created: String?,
    @SerializedName("ProductCategoryId")
    var ProductCategoryId: Int?,
    @SerializedName("Status")
    var Status: Int?
)