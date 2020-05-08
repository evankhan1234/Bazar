package com.evan.bazar.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Shop (
    @SerializedName("Id")
    var Id: String?,
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("Created")
    var Created: String?,
    @SerializedName("Address")
    var Address: String?,
    @SerializedName("LicenseNumber")
    var LicenseNumber: String?,
    @SerializedName("Status")
    var Status: String?,
    @SerializedName("ShopUserId")
    var ShopUserId: String?

): Parcelable