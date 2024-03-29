package com.evan.bazar.data.repositories

import com.evan.bazar.data.db.AppDatabase
import com.evan.bazar.data.db.entities.User
import com.evan.bazar.data.network.MyApi
import com.evan.bazar.data.network.PushApi
import com.evan.bazar.data.network.SafeApiRequest
import com.evan.bazar.data.network.post.*
import com.evan.bazar.data.network.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class HomeRepository (
    private val api: MyApi,
    private val push_api: PushApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    suspend fun getCategoryType(header:String): CategoryTypeResponse {
        return apiRequest { api.getCategoryType(header) }
    }
    suspend fun getCategorySearchType(header:String,searchPost: SearchCategoryPost): CategoryTypeResponse {
        return apiRequest { api.getCategorySearchType(header,searchPost) }
    }
    suspend fun getCategoryTypePagination(header:String,post:CategoryPost): CategoryTypeResponse {
        return apiRequest { api.categoryPagination(header,post) }
    }
    suspend fun postCategoryType(header:String,categoryPost: CategoryType): CategoryTypePostResponse {
        return apiRequest { api.createCategory(header,categoryPost!!) }
    }
    suspend fun postUpdateCategoryType(header:String,categoryPost: CategoryUpdate): CategoryTypePostResponse {
        return apiRequest { api.updateCategory(header,categoryPost!!) }
    }
    suspend fun getSupplierPagination(header:String,post:LimitPost): SupplierResponses {
        return apiRequest { api.supplierPagination(header,post) }
    }
    suspend fun postSupplier(header:String,supplierPost:  SupplierPost): CategoryTypePostResponse {
        return apiRequest { api.createSupplier(header,supplierPost!!) }
    }
    suspend fun postUpdateSupplier(header:String,supplierUpdatePost:  SupplierUpdatePost): CategoryTypePostResponse {
        return apiRequest { api.updateSupplier(header,supplierUpdatePost!!) }
    }

    suspend fun getPurchasePagination(header:String,post:LimitPost): PurchaseResponses {
        return apiRequest { api.purchasePagination(header,post) }
    }
    suspend fun getUnit(): UnitResponses {
        return apiRequest { api.getUnit() }
    }
    suspend fun postPurchase(header:String,purchasePost:  PurchasePost): CategoryTypePostResponse {
        return apiRequest { api.createPurchase(header,purchasePost!!) }
    }
    suspend fun updatePurchase(header:String,purchaseUpdatePost:  PurchaseUpdatePost): CategoryTypePostResponse {
        return apiRequest { api.updatePurchase(header,purchaseUpdatePost!!) }
    }

    suspend fun getProductPagination(header:String,post:LimitPost): ProductResponses {
        return apiRequest { api.productPagination(header,post) }
    }

    suspend fun postProduct(header:String,productPost:  ProductPost): CategoryTypePostResponse {
        return apiRequest { api.createProduct(header,productPost!!) }
    }
    suspend fun postUpdateProduct(header:String,productUpdatePost:  ProductUpdatePost): CategoryTypePostResponse {
        return apiRequest { api.updateProduct(header,productUpdatePost!!) }
    }
    suspend fun getSupplier(header:String): SupplierResponses {
        return apiRequest { api.getSuppliers(header) }
    }
    suspend fun getCategory(header:String): CategoryTypeResponse {
        return apiRequest { api.getCategory(header) }
    }

    suspend fun getSupplierSearch(header:String,searchPost: SearchCategoryPost): SupplierResponses {
        return apiRequest { api.getSupplierSearch(header,searchPost) }
    }
    suspend fun getPurchaseSearch(header:String,searchPost: SearchCategoryPost): PurchaseResponses {
        return apiRequest { api.getPurchaseSearch(header,searchPost) }
    }
    suspend fun getProductSearch(header:String,searchPost: SearchCategoryPost): ProductResponses {
        return apiRequest { api.getProductSearch(header,searchPost) }
    }
    suspend fun getShopUserDetails(header:String): ShopUserResponse {
        return apiRequest { api.getShopUserDetails(header) }
    }

    suspend fun getOrders(header:String): OrderListResponses {
        return apiRequest { api.getOrders(header) }
    }
    suspend fun getCustomerOrders(header:String,customerOrderPost: CustomerOrderPost): CustomerOrderResponses {
        return apiRequest { api.getCustomerOrder(header,customerOrderPost) }
    }

    suspend fun postDelivery(header:String,post:DeliveryOrderPost): BasicResponses {
        return apiRequest { api.postDelivery(header,post) }
    }
    suspend fun updateCustomerOrderStatus(header:String,post:CustomerOrderStatus): BasicResponses {
        return apiRequest { api.updateCustomerOrderStatus(header,post) }
    }
    suspend fun deleteCustomerOrderItem(header:String,post:CustomerOrderItem): BasicResponses {
        return apiRequest { api.deleteCustomerOrderItem(header,post) }
    }
    suspend fun updateOrderDetailsStatus(header:String,post:CustomerOrderDetailsStatus): BasicResponses {
        return apiRequest { api.updateOrderDetailsStatus(header,post) }
    }
    suspend fun updateQuantityStatus(header:String,post:QuantityPost): BasicResponses {
        return apiRequest { api.updateQuantityStatus(header,post) }
    }
    suspend fun getDeliveryList(header:String,post:LimitPost): DeliveryResponses {
        return apiRequest { api.getDeliveryList(header,post) }
    }
    suspend fun updateDeliveryStatus(header:String,post:DeliveryStatusPost): BasicResponses {
        return apiRequest { api.updateDeliveryStatus(header,post) }
    }
    suspend fun getNotice(header:String,post: NoticePost): NoticeResponses {
        return apiRequest { api.getNotice(header,post) }
    }
    suspend fun getPublicPostPagination(header:String,post: PublicForPost): PostResponses {
        return apiRequest { api.getPublicPostPagination(header,post) }
    }
    suspend fun getOwnPostPagination(header:String,post: OwnForPost): PostResponses {
        return apiRequest { api.getOwnPostPagination(header,post) }
    }
    suspend fun updatedLikeCount(header:String,post: LikeCountPost): BasicResponses {
        return apiRequest { api.updatedLikeCount(header,post) }
    }
    suspend fun createdLove(header:String,post: LovePost): BasicResponses {
        return apiRequest { api.createdLove(header,post) }
    }
    suspend fun deletedLove(header:String,post: LovePost): BasicResponses {
        return apiRequest { api.deletedLove(header,post) }
    }
    suspend fun createdNewsFeedPost(header:String,post: NewsfeedPost): BasicResponses {
        return apiRequest { api.createdNewsFeedPost(header,post) }
    }
    suspend fun getComments(header:String,post: CommentsPost): CommentsResponse {
        return apiRequest { api.getComments(header,post) }
    }
    suspend fun createComments(header:String,post: CommentsForPost): BasicResponses {
        return apiRequest { api.createComments(header,post) }
    }
    suspend fun updateOwnPost(header:String,post: OwnUpdatedPost): BasicResponses {
        return apiRequest { api.updateOwnPost(header,post) }
    }
    suspend fun createdLike(header:String,post: CommentsPost): BasicResponses {
        return apiRequest { api.createdLike(header,post) }
    }
    suspend fun deletedLike(header:String,post: CommentsPost): BasicResponses {
        return apiRequest { api.deletedLike(header,post) }
    }
    suspend fun updatedCommentsLikeCount(header:String,post: LikeCountPost): BasicResponses {
        return apiRequest { api.updatedCommentsLikeCount(header,post) }
    }
    suspend fun createReply(header:String,post: ReplyForPost): BasicResponses {
        return apiRequest { api.createReply(header,post) }
    }
    suspend fun getReply(header:String,post: ReplyPost): ReplyResponses {
        return apiRequest { api.getReply(header,post) }
    }
    suspend fun getStoreCount(header:String): StoreCountResponses {
        return apiRequest { api.getStoreCount(header) }
    }
    suspend fun getLasFive(header:String): LastFiveSalesCountResponses {
        return apiRequest { api.getLasFive(header) }
    }
    suspend fun getCustomerOrderCount(header:String): CustomerOrderCountResponses {
        return apiRequest { api.getCustomerOrderCount(header) }
    }
    suspend fun createToken(header:String,tokenPost: TokenPost): BasicResponses {
        return apiRequest { api.createToken(header,tokenPost) }
    }
    suspend fun getToken(header:String,tokenPost: TokenPost): TokenResponses {
        return apiRequest { api.getToken(header,tokenPost) }
    }
    suspend fun sendPush(header:String, post: PushPost): PushResponses {
        return apiRequest { push_api.sendPush(header,post) }
    }
    suspend fun updateShopUser(header:String, post: UserUpdatePost): BasicResponses {
        return apiRequest { api.updateShopUser(header,post) }
    }
    suspend fun updatePassword(header:String, post: PasswordPost): BasicResponses {
        return apiRequest { api.updatePassword(header,post) }
    }
    suspend fun createFirebaseId(header:String,tokenPost: TokenPost): BasicResponses {
        return apiRequest { api.createFirebaseId(header,tokenPost) }
    }
    suspend fun getFirebaseId(header:String,tokenPost: TokenPost): TokenResponses {
        return apiRequest { api.getFirebaseId(header,tokenPost) }
    }

    suspend fun getChatList(header:String,post: LimitPost): ChatListResponses {
        return apiRequest { api.getChatList(header,post) }
    }
    suspend fun getChatSearch(header:String,post: SearchCategoryPost): ChatListResponses {
        return apiRequest { api.getChatSearch(header,post) }
    }
    suspend fun getChatCount(header:String): CountResponses {
        return apiRequest { api.getChatCount(header) }
    }
    suspend fun updateChatCount(header:String,post: CustomerIdPost): BasicResponses {
        return apiRequest { api.updateChatCount(header,post) }
    }
    suspend fun getSystemList(header:String,post: SystemPost): SystemListResponses {
        return apiRequest { api.getSystemList(header,post) }
    }
    suspend fun getSystemSearchList(header:String,post: SystemSearchPost): SystemListResponses {
        return apiRequest { api.getSystemSearchList(header,post) }
    }

    suspend fun getDeliveryCharge(header:String): DeliveryAmountResponses {
        return apiRequest { api.getDeliveryCharge(header) }
    }

    suspend fun getCustomerOrdersList(header:String,customerOrderPost: CustomerOrderPost): CustomerOrderListResponses {
        return apiRequest { api.getCustomerOrderList(header,customerOrderPost) }
    }
    suspend fun getCustomerOrderInformation(header:String,post: CustomerOrderPost): CustomerDeliveryResponses {
        return apiRequest { api.getCustomerOrderInformation(header,post) }
    }
    suspend fun updateReturnOrderStatus(header:String,post: OrderReasonStatusPost): BasicResponses {
        return apiRequest { api.updateReturnOrderStatus(header,post) }
    }
}