package com.evan.bazar.data.network


import com.evan.bazar.data.network.post.*
import com.evan.bazar.data.network.responses.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @POST("login-api.php")
    suspend fun userLoginFor(
        @Body authPost: AuthPost
    ): Response<LoginResponse>


    @POST("create-user-api.php")
    suspend fun userRegistrationFor(
        @Body registrationPost: RegistrationPost
    ): Response<RegistrationResponse>

    //    @Multipart
//    @POST("create-profile-image-api.php")
//    suspend fun createProfileImage(
//        @Header("Authorization") test:String,
//        @Part file: MultipartBody.Part?, @Part("uploaded_file") requestBody: RequestBody?
//    ) : Response<ImageResponse>
    @Multipart
    @POST("create-sign-up-image.php")
    suspend fun createProfileImage(
        @Part file: MultipartBody.Part?, @Part("uploaded_file") requestBody: RequestBody?
    ): Response<ImageResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @GET("shop-type.php")
    suspend fun getShopType(
    ): Response<ShopTypeResponses>
    @GET("product-category-type-get.php")
    suspend fun getCategoryType(
        @Header("Authorization") Authorization:String
    ): Response<CategoryTypeResponse>

    @POST("category-pagination.php")
    suspend fun categoryPagination(
        @Header("Authorization") Authorization:String,
        @Body categoryPost: CategoryPost
    ): Response<CategoryTypeResponse>

    @POST("product-category.php")
    suspend fun createCategory(
        @Header("Authorization") Authorization:String,
        @Body categoryPost: CategoryType
    ): Response<CategoryTypePostResponse>
    @POST("update-product-category.php")
    suspend fun updateCategory(
        @Header("Authorization") Authorization:String,
        @Body categoryPost: CategoryUpdate
    ): Response<CategoryTypePostResponse>
    @GET("quotes")
    suspend fun getQuotes(): Response<QuotesResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("http://192.168.0.106/stationary/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}

