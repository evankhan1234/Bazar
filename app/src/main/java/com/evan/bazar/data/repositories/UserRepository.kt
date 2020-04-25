package com.evan.bazar.data.repositories

import com.evan.bazar.data.db.AppDatabase
import com.evan.bazar.data.db.entities.User
import com.evan.bazar.data.network.MyApi
import com.evan.bazar.data.network.SafeApiRequest
import com.evan.bazar.data.network.post.AuthPost
import com.evan.bazar.data.network.post.LoginResponse
import com.evan.bazar.data.network.responses.AuthResponse
import com.evan.bazar.data.network.responses.ImageResponse
import com.evan.bazar.data.network.responses.ShopTypeResponses
import okhttp3.MultipartBody
import okhttp3.RequestBody


class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }
    suspend fun userLoginFor(auth: AuthPost): LoginResponse {
        return apiRequest { api.userLoginFor(auth) }
    }
    suspend fun getShopType(): ShopTypeResponses {
        return apiRequest { api.getShopType() }
    }
    suspend fun createImage(part: MultipartBody.Part, body: RequestBody): ImageResponse {
        return apiRequest { api.createProfileImage(part,body) }
    }
    suspend fun userSignup(
        name: String,
        email: String,
        password: String
    ) : AuthResponse {
        return apiRequest{ api.userSignup(name, email, password)}
    }

    suspend fun saveUser(user: User) =
        db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getuser()
    fun getUserList() = db.getUserDao().getuserList()

}