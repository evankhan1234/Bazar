package com.evan.bazar.data.network


import android.annotation.SuppressLint
import com.evan.bazar.data.network.post.*
import com.evan.bazar.data.network.responses.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

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
    @POST("notice-get.php")
    suspend fun getNotice(
        @Header("Authorization") Authorization:String,
        @Body noticePost: NoticePost
    ): Response<NoticeResponses>

    @POST("create-user-api.php")
    suspend fun userRegistrationFor(
        @Body registrationPost: RegistrationPost
    ): Response<RegistrationResponse>
    @POST("post-pagination.php")
    suspend fun  getPublicPostPagination(
        @Header("Authorization") Authorization:String,
        @Body publicForPost: PublicForPost
    ): Response<PostResponses>
    @POST("own-post-pagination.php")
    suspend fun  getOwnPostPagination(
        @Header("Authorization") Authorization:String,
        @Body ownForPost: OwnForPost
    ): Response<PostResponses>
    @POST("comments-get.php")
    suspend fun getComments(
        @Header("Authorization") Authorization:String,
        @Body post: CommentsPost
    ): Response<CommentsResponse>
    @POST("reply-get.php")
    suspend fun getReply(
        @Header("Authorization") Authorization:String,
        @Body post: ReplyPost
    ): Response<ReplyResponses>
    @POST("create-comments.php")
    suspend fun createComments(
        @Header("Authorization") Authorization:String,
        @Body post: CommentsForPost
    ): Response<BasicResponses>
    @POST("create-reply.php")
    suspend fun createReply(
        @Header("Authorization") Authorization:String,
        @Body post: ReplyForPost
    ): Response<BasicResponses>
    @POST("update-own-post.php")
    suspend fun updateOwnPost(
        @Header("Authorization") Authorization:String,
        @Body post: OwnUpdatedPost
    ): Response<BasicResponses>

    @POST("create-likes.php")
    suspend fun createdLike(
        @Header("Authorization") Authorization:String,
        @Body post: CommentsPost
    ): Response<BasicResponses>
    @POST("deleted-like.php")
    suspend fun deletedLike(
        @Header("Authorization") Authorization:String,
        @Body post: CommentsPost
    ): Response<BasicResponses>
    @POST("update-comments-like.php")
    suspend fun updatedCommentsLikeCount(
        @Header("Authorization") Authorization:String,
        @Body post: LikeCountPost
    ): Response<BasicResponses>
    @POST("update-like-count.php")
    suspend fun updatedLikeCount(
        @Header("Authorization") Authorization:String,
        @Body post: LikeCountPost
    ): Response<BasicResponses>
    @POST("create-love.php")
    suspend fun createdLove(
        @Header("Authorization") Authorization:String,
        @Body post: LovePost
    ): Response<BasicResponses>
    @POST("deleted-love.php")
    suspend fun deletedLove(
        @Header("Authorization") Authorization:String,
        @Body post: LovePost
    ): Response<BasicResponses>
    @POST("create-post.php")
    suspend fun createdNewsFeedPost(
        @Header("Authorization") Authorization:String,
        @Body post: NewsfeedPost
    ): Response<BasicResponses>
    //    @Multipart
//    @POST("create-profile-image-api.php")
//    suspend fun createProfileImage(
//        @Header("Authorization") test:String,
//        @Part file: MultipartBody.Part?, @Part("uploaded_file") requestBody: RequestBody?
//    ) : Response<ImageResponse>
    @GET("get-delivery-charge.php")
    suspend fun getDeliveryCharge(
        @Header("Authorization") Authorization:String
    ): Response<DeliveryAmountResponses>
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

    @POST("update-return-order-status.php")
    suspend fun updateReturnOrderStatus(
        @Header("Authorization") Authorization:String,
        @Body post: OrderReasonStatusPost
    ): Response<BasicResponses>
    @POST("user-token.php")
    suspend fun createToken(
        @Header("Authorization") Authorization:String,
        @Body tokenPost: TokenPost
    ): Response<BasicResponses>
    @POST("user-firebaseid.php")
    suspend fun createFirebaseId(
        @Header("Authorization") Authorization:String,
        @Body tokenPost: TokenPost
    ): Response<BasicResponses>
    @POST("get-token.php")
    suspend fun getToken(
        @Header("Authorization") Authorization:String,
        @Body tokenPost: TokenPost
    ): Response<TokenResponses>
    @POST("get-firebase-id.php")
    suspend fun getFirebaseId(
        @Header("Authorization") Authorization:String,
        @Body tokenPost: TokenPost
    ): Response<TokenResponses>
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

    @POST("customer-order-products-delivery.php")
    suspend fun getCustomerOrderList(
        @Header("Authorization") Authorization:String,
        @Body customerOrderPost: CustomerOrderPost
    ): Response<CustomerOrderListResponses>
    @POST("get-delivery-customer-order-details.php")
    suspend fun getCustomerOrderInformation(
        @Header("Authorization") Authorization:String,
        @Body post: CustomerOrderPost
    ): Response<CustomerDeliveryResponses>

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

    @POST("searching-chat-list.php")
    suspend fun getChatSearch(
        @Header("Authorization") Authorization:String,
        @Body searchPost: SearchCategoryPost
    ): Response<ChatListResponses>

    @GET("chat-list-count.php")
    suspend fun getChatCount(
        @Header("Authorization") Authorization:String
    ): Response<CountResponses>
    @POST("system-product-pagination.php")
    suspend fun getSystemList(
        @Header("Authorization") Authorization:String,
        @Body systemPost: SystemPost
    ): Response<SystemListResponses>
    @POST("searching-system-list.php")
    suspend fun getSystemSearchList(
        @Header("Authorization") Authorization:String,
        @Body systemPost: SystemSearchPost
    ): Response<SystemListResponses>
    @POST("update-chat-seen.php")
    suspend fun updateChatCount(
        @Header("Authorization") Authorization:String,
        @Body customerIdPost: CustomerIdPost
    ): Response<BasicResponses>
    @POST("chatlist-pagination.php")
    suspend fun getChatList(
        @Header("Authorization") Authorization:String,
        @Body limitPost: LimitPost
    ): Response<ChatListResponses>
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
    @POST("update-shop-user-password.php")
    suspend fun  updatePassword(
        @Header("Authorization") Authorization:String,
        @Body passwordPost: PasswordPost
    ): Response<BasicResponses>
    @POST("searching-product.php")
    suspend fun getProductSearch(
        @Header("Authorization") Authorization:String,
        @Body searchPost: SearchCategoryPost
    ): Response<ProductResponses>
    @POST("update-shop-user-details.php")
    suspend fun updateShopUser(
        @Header("Authorization") Authorization:String,
        @Body userUpdatePost: UserUpdatePost
    ): Response<BasicResponses>
    @GET("supplier-get.php")
    suspend fun getSuppliers(
        @Header("Authorization") Authorization:String
    ): Response<SupplierResponses>
    @GET("get-shopuser-store.php")
    suspend fun getStoreCount(
        @Header("Authorization") Authorization:String
    ): Response<StoreCountResponses>
    @GET("get-order-count.php")
    suspend fun getCustomerOrderCount(
        @Header("Authorization") Authorization:String
    ): Response<CustomerOrderCountResponses>
    @GET("get-last-five-sales.php")
    suspend fun getLasFive(
        @Header("Authorization") Authorization:String
    ): Response<LastFiveSalesCountResponses>
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
    @POST("create-purchase-api.php")
    suspend fun createPurchase(
        @Header("Authorization") Authorization:String,
        @Body purchasePost: PurchasePost
    ): Response<CategoryTypePostResponse>
    @POST("update-purchase-api.php")
    suspend fun updatePurchase(
        @Header("Authorization") Authorization:String,
        @Body purchaseUpdatePost: PurchaseUpdatePost
    ): Response<CategoryTypePostResponse>
    @POST("update-supplier-api.php")
    suspend fun updateSupplier(
        @Header("Authorization") Authorization:String,
        @Body supplierUpdatePost: SupplierUpdatePost
    ): Response<CategoryTypePostResponse>
    @POST("update-product-category.php")
    suspend fun updateCategory(
        @Header("Authorization") Authorization:String,
        @Body categoryPost: CategoryUpdate
    ): Response<CategoryTypePostResponse>
    @GET("quotes")
    suspend fun getQuotes(): Response<QuotesResponse>

    companion object {
        @SuppressLint("NewApi")
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                //.baseUrl("http://206.189.180.190/v1/")
                .baseUrl("http://192.168.0.103/stationary/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}

