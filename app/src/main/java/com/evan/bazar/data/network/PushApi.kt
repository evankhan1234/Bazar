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

interface PushApi {

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

    @POST("update-delivery-api.php")
    suspend fun updateDeliveryStatus(
        @Header("Authorization") Authorization:String,
        @Body deliveryStatusPost: DeliveryStatusPost
    ): Response<BasicResponses>
    @Multipart
    @POST("create-sign-up-image.php")
    suspend fun createProfileImage(
        @Part file: MultipartBody.Part?, @Part("uploaded_file") requestBody: RequestBody?
    ): Response<ImageResponse>
    @POST("deliveriess-pagination.php")
    suspend fun getDeliveryList(
        @Header("Authorization") Authorization:String,
        @Body limitPost: LimitPost
    ): Response<DeliveryResponses>
    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>
    @POST("update_customer_order_quantity.php")
    suspend fun updateQuantityStatus(
        @Header("Authorization") Authorization:String,
        @Body quantityPost: QuantityPost
    ): Response<BasicResponses>
    @POST("update_customer_order_status_details.php")
    suspend fun updateOrderDetailsStatus(
        @Header("Authorization") Authorization:String,
        @Body customerOrderDetailsStatus: CustomerOrderDetailsStatus
    ): Response<BasicResponses>
    @POST("delete_customer_order_items.php")
    suspend fun deleteCustomerOrderItem(
        @Header("Authorization") Authorization:String,
        @Body customerOrderItem: CustomerOrderItem
    ): Response<BasicResponses>
    @POST("update_customer_order_status.php")
    suspend fun updateCustomerOrderStatus(
        @Header("Authorization") Authorization:String,
        @Body customerOrderStatus: CustomerOrderStatus
    ): Response<BasicResponses>
    @POST("create-delivery-api.php")
    suspend fun postDelivery(
        @Header("Authorization") Authorization:String,
        @Body deliveryOrderPost: DeliveryOrderPost
    ): Response<BasicResponses>
    @POST("order-get-shop.php")
    suspend fun getCustomerOrder(
        @Header("Authorization") Authorization:String,
        @Body customerOrderPost: CustomerOrderPost
    ): Response<CustomerOrderResponses>
    @GET("order-get.php")
    suspend fun getOrders(
        @Header("Authorization") Authorization:String
    ): Response<OrderListResponses>
    @GET("shop-type.php")
    suspend fun getShopType(
    ): Response<ShopTypeResponses>
    @GET("product-category-type-get.php")
    suspend fun getCategoryType(
        @Header("Authorization") Authorization:String
    ): Response<CategoryTypeResponse>
    @POST("searching-pagination.php")
    suspend fun getCategorySearchType(
        @Header("Authorization") Authorization:String,
        @Body searchPost: SearchCategoryPost
    ): Response<CategoryTypeResponse>
    @POST("searching-supplier.php")
    suspend fun getSupplierSearch(
        @Header("Authorization") Authorization:String,
        @Body searchPost: SearchCategoryPost
    ): Response<SupplierResponses>
    @POST("searching-purchase.php")
    suspend fun getPurchaseSearch(
        @Header("Authorization") Authorization:String,
        @Body searchPost: SearchCategoryPost
    ): Response<PurchaseResponses>
    @POST("searching-product.php")
    suspend fun getProductSearch(
        @Header("Authorization") Authorization:String,
        @Body searchPost: SearchCategoryPost
    ): Response<ProductResponses>

    @GET("supplier-get.php")
    suspend fun getSuppliers(
        @Header("Authorization") Authorization:String
    ): Response<SupplierResponses>
    @GET("shop-user-details.php")
    suspend fun getShopUserDetails(
        @Header("Authorization") Authorization:String
    ): Response<ShopUserResponse>

    @GET("category-get.php")
    suspend fun getCategory(
        @Header("Authorization") Authorization:String
    ): Response<CategoryTypeResponse>
    @GET("unit-details.php")
    suspend fun getUnit(
    ): Response<UnitResponses>

    @POST("category-pagination.php")
    suspend fun categoryPagination(
        @Header("Authorization") Authorization:String,
        @Body categoryPost: CategoryPost
    ): Response<CategoryTypeResponse>

    @POST("supplier-pagination.php")
    suspend fun supplierPagination(
        @Header("Authorization") Authorization:String,
        @Body limitPost: LimitPost
    ): Response<SupplierResponses>

    @POST("purchase-pagination.php")
    suspend fun purchasePagination(
        @Header("Authorization") Authorization:String,
        @Body limitPost: LimitPost
    ): Response<PurchaseResponses>
    @POST("product-pagination.php")
    suspend fun productPagination(
        @Header("Authorization") Authorization:String,
        @Body limitPost: LimitPost
    ): Response<ProductResponses>

    @POST("product-category.php")
    suspend fun createCategory(
        @Header("Authorization") Authorization:String,
        @Body categoryPost: CategoryType
    ): Response<CategoryTypePostResponse>
    @POST("create-suppplier-api.php")
    suspend fun createSupplier(
        @Header("Authorization") Authorization:String,
        @Body supplierPost: SupplierPost
    ): Response<CategoryTypePostResponse>

    @POST("create-product-api.php")
    suspend fun createProduct(
        @Header("Authorization") Authorization:String,
        @Body productPost: ProductPost
    ): Response<CategoryTypePostResponse>

    @POST("update-product-api.php")
    suspend fun updateProduct(
        @Header("Authorization") Authorization:String,
        @Body productUpdatePost: ProductUpdatePost
    ): Response<CategoryTypePostResponse>


    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): PushApi {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("http://192.168.0.106/stationary/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PushApi::class.java)
        }
    }

}

