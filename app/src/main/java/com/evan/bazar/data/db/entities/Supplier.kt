package com.evan.bazar.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Supplier (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("Email")
    var Email: String?,
    @SerializedName("Address")
    var Address: String?,
    @SerializedName("ContactNumber")
    var ContactNumber: String?,
    @SerializedName("SupplierImage")
    var SupplierImage: String?,
    @SerializedName("Details")
    var Details: String?,
    @SerializedName("ShopId")
    var ShopId: Int?,
    @SerializedName("ShopUserId")
    var ShopUserId: Int?,
    @SerializedName("Created")
    var Created: String?,
    @SerializedName("Status")
    var Status: Int?
): Parcelable