package com.evan.bazar.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Unit  (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("Status")
    var Status: String?,
    @SerializedName("Created")
    var Created: String?
): Parcelable