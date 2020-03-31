package com.evan.bazar.data.network.post

import com.evan.bazar.data.db.entities.User

data  class AuthPost(

    val Email: String?,
    val Password: String?,
    val OwnerMobileNumber: String?
)