package com.evan.bazar.data.network.post

import com.google.gson.annotations.SerializedName

class PasswordPost(
    @SerializedName("Password")
    val Password: String?
)