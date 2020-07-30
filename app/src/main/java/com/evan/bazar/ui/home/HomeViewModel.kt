package com.evan.bazar.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.evan.bazar.data.network.post.*
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.ui.home.category.ICategoryListener
import com.evan.bazar.ui.home.category.ICreateCategoryListener
import com.evan.bazar.ui.home.chat.IChatCountListener
import com.evan.bazar.ui.home.dashboard.ICustomerOrderCountListener
import com.evan.bazar.ui.home.dashboard.ILastFiveSalesListener
import com.evan.bazar.ui.home.dashboard.IPushListener
import com.evan.bazar.ui.home.dashboard.IStoreCountListener
import com.evan.bazar.ui.home.delivery.ICustomerOrderListListener
import com.evan.bazar.ui.home.delivery.IDeleteListener
import com.evan.bazar.ui.home.delivery.IDeliveryChargeListener
import com.evan.bazar.ui.home.delivery.IDeliveryPostListener
import com.evan.bazar.ui.home.delivery.details.ICustomerOrderListForListener
import com.evan.bazar.ui.home.delivery.details.ICustomerOrderListener
import com.evan.bazar.ui.home.newsfeed.ownpost.IPostListener
import com.evan.bazar.ui.home.newsfeed.publicpost.comments.ICommentsListener
import com.evan.bazar.ui.home.newsfeed.publicpost.comments.ICommentsPostListener
import com.evan.bazar.ui.home.newsfeed.publicpost.comments.ISucceslistener
import com.evan.bazar.ui.home.newsfeed.publicpost.reply.IReplyListener
import com.evan.bazar.ui.home.newsfeed.publicpost.reply.IReplyPostListener
import com.evan.bazar.ui.home.order.IOrdersListListener
import com.evan.bazar.ui.home.product.ICategoryTypeListener
import com.evan.bazar.ui.home.product.ICreateProductListener
import com.evan.bazar.ui.home.product.ISupplierListener
import com.evan.bazar.ui.home.purchase.ICreatePurchaseListener
import com.evan.bazar.ui.home.purchase.IUnitListener
import com.evan.bazar.ui.home.settings.IShopUserListener
import com.evan.bazar.ui.home.settings.password.IChangePasswordListener
import com.evan.bazar.ui.home.settings.profile.IProfileUpdateListener
import com.evan.bazar.ui.home.supplier.ICreateSupplierListener
import com.evan.bazar.util.*
import com.google.gson.Gson

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {
    var categoryListener: ICategoryListener?=null
    var createCategoryListener: ICreateCategoryListener?=null
    var createSupplierListener: ICreateSupplierListener?=null
    var createPurchaseListener: ICreatePurchaseListener?=null
    var createProductListener: ICreateProductListener?=null
    var deliveryPostListener: IDeliveryPostListener?=null
    var unitListener: IUnitListener?=null
    var supplierListener: ISupplierListener?=null
    var categoryTypeListener: ICategoryTypeListener?=null
    var orderListListener: IOrdersListListener?=null
    var shopUserListener: IShopUserListener?=null
    var deleteListener: IDeleteListener?=null
    var typePost: CategoryType? = null
    var supplierPost: SupplierPost? = null
    var purchasePost: PurchasePost? = null
    var productPost: ProductPost? = null
    var deliveryOrderPost: DeliveryOrderPost? = null
    var customerOrderStatus: CustomerOrderStatus? = null
    var customerOrderDetailsStatus: CustomerOrderDetailsStatus? = null
    var quantityPost: QuantityPost? = null
    var commentsPost:CommentsPost?=null
    var replyPost:ReplyPost?=null
    var tokenPost:TokenPost?=null
    var customerIdPost:CustomerIdPost?=null
    var passwordPost:PasswordPost?=null
    var userUpdatePost:UserUpdatePost?=null
    var ownUpdatedPost:OwnUpdatedPost?=null
    var likeCountPost:LikeCountPost?=null
    var commentsForPost:CommentsForPost?=null
    var replyForPost:ReplyForPost?=null
    var customerOrderItem: CustomerOrderItem? = null
    var productUpdatePost: ProductUpdatePost? = null
    var purchaseUpdatePost: PurchaseUpdatePost? = null
    var supplierUpdatePost: SupplierUpdatePost? = null
    var customerOrderListener: ICustomerOrderListListener? = null
    var customerOrderForListener: ICustomerOrderListForListener? = null
    var customerOrderInformationListener: ICustomerOrderListener? = null
    var typeUpdatePost: CategoryUpdate? = null
    var customerOrderPost: CustomerOrderPost? = null
    var orderReasonStatusPost: OrderReasonStatusPost? = null
    var postListener: IPostListener?=null
    var commentsPostListener: ICommentsPostListener?=null
    var commentsListener: ICommentsListener?=null
    var succeslistener: ISucceslistener?=null
    var replyListener: IReplyListener?=null
    var replyPostListener: IReplyPostListener?=null
    var newsfeedPost:NewsfeedPost?=null
    var storeCountListener: IStoreCountListener?=null
    var lastFiveSalesListener: ILastFiveSalesListener?=null
    var customerOrderCountListener: ICustomerOrderCountListener?=null
    var pushListener: IPushListener?=null
    var chatCountListener: IChatCountListener?=null
    var profileUpdateListener: IProfileUpdateListener?=null
    var changePasswordListener: IChangePasswordListener?=null
    var deliveryChargeListener: IDeliveryChargeListener?=null
    fun getCategoryType(header:String) {
        categoryListener?.started()
        Coroutines.main {
            try {
                val response = repository.getCategoryType(header)
                  categoryListener?.show(response?.data!!)
                categoryListener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                categoryListener?.end()
            } catch (e: NoInternetException) {
                categoryListener?.end()
            }
        }

    }
    fun postCategoryType(header:String,name:String,status:Int,shopId:Int,created:String) {
        categoryListener?.started()
        Coroutines.main {
            try {
                typePost = CategoryType(name!!, status!!, shopId!!,created!!)
                val response = repository.postCategoryType(header,typePost!!)
                createCategoryListener?.show(response?.message!!)
                createCategoryListener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                createCategoryListener?.end()
            } catch (e: NoInternetException) {
                createCategoryListener?.end()
            }
        }

    }
    fun postUpdateCategoryType(header:String,id:Int,name:String,status:Int,shopId:Int,created:String) {
        categoryListener?.started()
        Coroutines.main {
            try {
                typeUpdatePost = CategoryUpdate(id!!,name!!, status!!, shopId!!,created!!)
                val response = repository.postUpdateCategoryType(header,typeUpdatePost!!)
                createCategoryListener?.show(response?.message!!)
                createCategoryListener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                createCategoryListener?.end()
            } catch (e: NoInternetException) {
                createCategoryListener?.end()
            }
        }

    }
    fun postSupplier(header:String,name:String,phone:String,email:String,address:String,details:String,image:String,status:Int,shopId:Int,created:String) {
        createSupplierListener?.started()
        Coroutines.main {
            try {
                supplierPost = SupplierPost(name!!,phone!!,email!!,address!!,details!!,image!!,status!!, shopId!!,created!!)
                Log.e("response", "response" + Gson().toJson(supplierPost))
                val response = repository.postSupplier(header,supplierPost!!)
                if(response.status==200){
                    createSupplierListener?.show(response?.message!!)
                    createSupplierListener?.end()
                    Log.e("response", "response" + Gson().toJson(response))
                }
                else{
                    createSupplierListener?.failure(response?.message!!)
                    createSupplierListener?.end()
                    Log.e("response", "response" + Gson().toJson(response))
                }


            } catch (e: ApiException) {
                createSupplierListener?.failure(e?.message!!)
                createSupplierListener?.end()
            } catch (e: NoInternetException) {
                createSupplierListener?.end()
            }
        }

    }
    fun postUpdateSupplier(header:String,id:Int,name:String,phone:String,email:String,address:String,details:String,image:String,status:Int,shopId:Int,created:String) {
        createSupplierListener?.started()
        Coroutines.main {
            try {
                supplierUpdatePost = SupplierUpdatePost(id,name!!,phone!!,email!!,address!!,details!!,image!!,status!!, shopId!!,created!!)
                Log.e("response", "response" + Gson().toJson(supplierUpdatePost))
                val response = repository.postUpdateSupplier(header,supplierUpdatePost!!)
                createSupplierListener?.show(response?.message!!)
                createSupplierListener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                createSupplierListener?.end()
            } catch (e: NoInternetException) {
                createSupplierListener?.end()
            }
        }

    }
    fun getUnit() {
        Coroutines.main {
            try {
                val authResponse = repository.getUnit()
                unitListener?.unit(authResponse?.data!!)
                Log.e("response", "response" + Gson().toJson(authResponse))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun postPurchase(header:String,purchaseName:String,purchaseDetails:String,purchaseNo:String,purchaseDate:String,stock:Int,item:Int,quantity:Int,rate:Double,discount:Double,total:Double,grandTotal:Double,unitId:Int,shopId:Int,created:String,status:Int) {
        createPurchaseListener?.started()
        Coroutines.main {
            try {
                purchasePost = PurchasePost(purchaseName!!,purchaseDetails!!,purchaseNo!!,purchaseDate!!,stock!!,item!!,quantity!!, rate!!,discount!!, total!!,grandTotal!!, unitId!!,shopId!!,created!!,status!!)
                Log.e("response", "response" + Gson().toJson(purchasePost))
                val response = repository.postPurchase(header,purchasePost!!)
                createPurchaseListener?.show(response?.message!!)
                createPurchaseListener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                createPurchaseListener?.end()
                createPurchaseListener?.failue(e?.message!!)
            } catch (e: NoInternetException) {
                createPurchaseListener?.end()
            }
        }

    }
    fun updatePurchase(header:String,id: Int,purchaseName:String,purchaseDetails:String,purchaseNo:String,purchaseDate:String,stock:Int,item:Int,quantity:Int,rate:Double,discount:Double,total:Double,grandTotal:Double,unitId:Int,shopId:Int,created:String,status:Int) {
        createPurchaseListener?.started()
        Coroutines.main {
            try {
                purchaseUpdatePost = PurchaseUpdatePost(id!!,purchaseName!!,purchaseDetails!!,purchaseNo!!,purchaseDate!!,stock!!,item!!,quantity!!, rate!!,discount!!, total!!,grandTotal!!, unitId!!,shopId!!,created!!,status!!)
                Log.e("response", "response" + Gson().toJson(purchaseUpdatePost))
                val response = repository.updatePurchase(header,purchaseUpdatePost!!)
                createPurchaseListener?.show(response?.message!!)
                createPurchaseListener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                createPurchaseListener?.end()
            } catch (e: NoInternetException) {
                createPurchaseListener?.end()
            }
        }

    }
    fun getCategory(token:String) {
        Coroutines.main {
            try {
                val authResponse = repository.getCategory(token)
                categoryTypeListener?.category(authResponse?.data!!)
                Log.e("response", "response" + Gson().toJson(authResponse))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun getSupplier(token:String) {
        Coroutines.main {
            try {
                val authResponse = repository.getSupplier(token)
                supplierListener?.supplier(authResponse?.data!!)
                Log.e("response", "response" + Gson().toJson(authResponse))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun postProduct(header:String,name:String,details:String,productCode:String,productImage:String,unitId:Int,sellPrice:Double,supplierPrice:Double,shopId:Int,stock:Int,discount:Double,created:String,categoryId:Int,status:Int) {
        createProductListener?.started()
        Coroutines.main {
            try {
                productPost = ProductPost(name!!,details!!,productCode!!,productImage!!,unitId!!,sellPrice!!,supplierPrice!!,shopId!!, stock!!,discount!!, created!!,categoryId!!,status!!)
                Log.e("response", "response" + Gson().toJson(productPost))
                val response = repository.postProduct(header,productPost!!)
                createProductListener?.show(response?.message!!)
                createProductListener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                createProductListener?.end()
                createProductListener?.failure(e?.message!!)
            } catch (e: NoInternetException) {
                createProductListener?.end()
            }
        }

    }
    fun postUpdateProduct(header:String,id:Int,name:String,details:String,productCode:String,productImage:String,unitId:Int,sellPrice:Double,supplierPrice:Double,shopId:Int,stock:Int,discount:Double,created:String,categoryId:Int,status:Int) {
        createProductListener?.started()
        Coroutines.main {
            try {
                productUpdatePost = ProductUpdatePost(id!!,name!!,details!!,productCode!!,productImage!!,unitId!!,sellPrice!!,supplierPrice!!,shopId!!, stock!!,discount!!, created!!,categoryId!!,status!!)
                Log.e("response", "response" + Gson().toJson(productUpdatePost))
                val response = repository.postUpdateProduct(header,productUpdatePost!!)
                createProductListener?.show(response?.message!!)
                createProductListener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                createProductListener?.end()
            } catch (e: NoInternetException) {
                createProductListener?.end()
            }
        }

    }
    fun getShopUserDetails(token:String) {
        shopUserListener?.onStarted()
        Coroutines.main {
            try {
                val authResponse = repository.getShopUserDetails(token)
                shopUserListener?.show(authResponse?.data!!)
                Log.e("response", "response" + Gson().toJson(authResponse))
                shopUserListener?.onEnd()
            } catch (e: ApiException) {
                shopUserListener?.onEnd()
            } catch (e: NoInternetException) {
                shopUserListener?.onEnd()
            }
        }

    }

    fun getOrders(token:String) {
        orderListListener?.onStarted()
        Coroutines.main {
            try {
                val authResponse = repository.getOrders(token)
                orderListListener?.order(authResponse?.data!!)
                Log.e("response", "response" + Gson().toJson(authResponse))
                orderListListener?.onEnd()
            } catch (e: ApiException) {
                orderListListener?.onEnd()
            } catch (e: NoInternetException) {
                orderListListener?.onEnd()
            }
        }

    }

    fun getCustomerOrders(token:String,id: Int) {
        customerOrderListener?.onStarted()
        Coroutines.main {
            try {
                customerOrderPost= CustomerOrderPost(id)
                val authResponse = repository.getCustomerOrders(token,customerOrderPost!!)
                customerOrderListener?.order(authResponse?.data!!)
                Log.e("response", "response" + Gson().toJson(authResponse))
                customerOrderListener?.onEnd()
            } catch (e: ApiException) {
                customerOrderListener?.onEnd()
            } catch (e: NoInternetException) {
                customerOrderListener?.onEnd()
            }
        }

    }

    fun postDelivery(header:String,customerId:Int,orderId:Int,discount:Double,grandTotal:Double,paidAmount:Double,dueAmount:Double,total:Double,status:Int,invoiceNumber:String,created:String,orderDetails:String,deliveryCharge:Double,latitude:Double,longitude:Double) {
        deliveryPostListener?.started()
        Coroutines.main {
            try {
                deliveryOrderPost = DeliveryOrderPost(customerId!!,orderId!!,discount!!,grandTotal!!,paidAmount!!,dueAmount!!,total!!, status!!,invoiceNumber!!, created!!,orderDetails!!, deliveryCharge!!,latitude,longitude)
                Log.e("response", "response" + Gson().toJson(deliveryOrderPost))
                val response = repository.postDelivery(header,deliveryOrderPost!!)
                deliveryPostListener?.show(response?.message!!)
                deliveryPostListener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                deliveryPostListener?.end()
            } catch (e: NoInternetException) {
                deliveryPostListener?.end()
            }
        }

    }
    fun updateCustomerOrderStatus(header:String,orderId:Int,status:Int) {
        Coroutines.main {
            try {
                customerOrderStatus= CustomerOrderStatus(orderId,status)
                Log.e("response", "response" + Gson().toJson(customerOrderStatus))
                val response = repository.updateCustomerOrderStatus(header,customerOrderStatus!!)
                Log.e("response", "response" + Gson().toJson(response))
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun deleteCustomerOrderItem(header:String,id:Int) {
        deleteListener?.onStartedView()
        Coroutines.main {
            try {
                customerOrderItem= CustomerOrderItem(id)
                Log.e("response", "response" + Gson().toJson(customerOrderItem))
                val response = repository.deleteCustomerOrderItem(header,customerOrderItem!!)
                if (response.success!!){
                    deleteListener?.onSuccess(response?.message!!)
                    deleteListener?.onEndView()
                }
                else{
                    deleteListener?.onFailure(response?.message!!)
                    deleteListener?.onEndView()
                }
                Log.e("response", "response" + Gson().toJson(response))
            } catch (e: ApiException) {
                deleteListener?.onFailure(e?.message!!)
                deleteListener?.onEndView()
            } catch (e: NoInternetException) {
                deleteListener?.onFailure(e?.message!!)
                deleteListener?.onEndView()
            }
        }

    }
    fun updateOrderDetailsStatus(header:String,data: MutableList<CustomerOrderStatus>?) {
        Coroutines.main {
            try {
                customerOrderDetailsStatus= CustomerOrderDetailsStatus(data)
                Log.e("response", "response" + Gson().toJson(customerOrderDetailsStatus))
                val response = repository.updateOrderDetailsStatus(header,customerOrderDetailsStatus!!)
                Log.e("response", "response" + Gson().toJson(response))
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun updateQuantityStatus(header:String,id:Int,quantity:Int) {
        Coroutines.main {
            try {
                quantityPost= QuantityPost(id,quantity)
                Log.e("response", "response" + Gson().toJson(quantityPost))
                val response = repository.updateQuantityStatus(header,quantityPost!!)
                Log.e("response", "response" + Gson().toJson(response))
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun createdNewsFeedPost(header:String,Name:String,content:String,picture:String,created:String,status:Int,type:Int,image:String,love:Int) {
        postListener?.onStarted()
        Coroutines.main {
            try {
                newsfeedPost= NewsfeedPost(Name!!,content!!,picture!!,created!!,status!!,type!!,image!!,love!!)
                Log.e("Search", "Search" + Gson().toJson(newsfeedPost))
                val response = repository.createdNewsFeedPost(header,newsfeedPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                postListener?.onSuccess(response?.message!!)
                postListener?.onEnd()

            } catch (e: ApiException) {
                postListener?.onEnd()
                postListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                postListener?.onEnd()
                postListener?.onFailure(e?.message!!)
            }
        }

    }
    fun getComments(header:String,postId:Int,type:Int) {
        commentsListener?.onStarted()
        Coroutines.main {
            try {
                commentsPost= CommentsPost(postId,type)
                Log.e("Search", "Search" + Gson().toJson(commentsPost))
                val response = repository.getComments(header,commentsPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))
                commentsListener?.load(response?.data!!)
                commentsListener?.onEnd()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {
                commentsListener?.onEnd()
            } catch (e: NoInternetException) {
                commentsListener?.onEnd()
            }
        }

    }
    fun getCommentsAgain(header:String,postId:Int,type:Int) {

        Coroutines.main {
            try {
                commentsPost= CommentsPost(postId,type)
                Log.e("Search", "Search" + Gson().toJson(commentsPost))
                val response = repository.getComments(header,commentsPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))

                succeslistener?.onShow()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }

    fun updateNewsFeedPost(header:String,id:Int,Name:String,content:String,picture:String,type:Int,image:String) {
        postListener?.onStarted()
        Coroutines.main {
            try {
                ownUpdatedPost= OwnUpdatedPost(id,Name!!,content!!,picture!!,type!!,image!!)
                Log.e("Search", "Search" + Gson().toJson(ownUpdatedPost))
                val response = repository.updateOwnPost(header,ownUpdatedPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                postListener?.onSuccess(response?.message!!)
                postListener?.onEnd()

            } catch (e: ApiException) {
                postListener?.onEnd()
                postListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                postListener?.onEnd()
                postListener?.onFailure(e?.message!!)
            }
        }

    }
    fun updatedCommentsLikeCount(header:String,id: Int,love:Int) {
        Coroutines.main {
            try {
                likeCountPost= LikeCountPost(id,love)
                val response = repository.updatedCommentsLikeCount(header,likeCountPost!!)
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun createdLike(header:String,postId: Int,type:Int) {
        Coroutines.main {
            try {
                commentsPost= CommentsPost(postId,type)
                val response = repository.createdLike(header,commentsPost!!)
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun deletedLike(header:String,postId: Int,type:Int) {
        Coroutines.main {
            try {
                commentsPost= CommentsPost(postId,type)
                val response = repository.deletedLike(header,commentsPost!!)
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun createComments(header:String,Name:String,content:String,created:String,status:Int,type:Int,image:String,love:Int,postId:Int) {
        commentsPostListener?.onStarted()
        Coroutines.main {
            try {
                commentsForPost= CommentsForPost(Name!!,content!!,created!!,status!!,type!!,image!!,love!!,postId!!)
                Log.e("Search", "Search" + Gson().toJson(newsfeedPost))
                val response = repository.createComments(header,commentsForPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                commentsPostListener?.onSuccess(response?.message!!)
                commentsPostListener?.onEnd()

            } catch (e: ApiException) {
                commentsPostListener?.onEnd()
                commentsPostListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                commentsPostListener?.onEnd()
                commentsPostListener?.onFailure(e?.message!!)
            }
        }

    }
    fun getReply(header:String,commentId:Int) {
        replyListener?.onStarted()
        Coroutines.main {
            try {
                replyPost= ReplyPost(commentId)
                Log.e("Search", "Search" + Gson().toJson(replyPost))
                val response = repository.getReply(header,replyPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))
                replyListener?.load(response?.data!!)
                replyListener?.onEnd()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {
                replyListener?.onEnd()
            } catch (e: NoInternetException) {
                replyListener?.onEnd()
            }
        }

    }
    fun createReply(header:String,Name:String,content:String,created:String,status:Int,type:Int,image:String,commentsId:Int) {
        replyPostListener?.onStarted()
        Coroutines.main {
            try {
                replyForPost= ReplyForPost(Name!!,content!!,created!!,status!!,type!!,image!!,commentsId!!)
                Log.e("Search", "Search" + Gson().toJson(replyForPost))
                val response = repository.createReply(header,replyForPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                replyPostListener?.onSuccess(response?.message!!)
                replyPostListener?.onEnd()

            } catch (e: ApiException) {
                replyPostListener?.onEnd()
                replyPostListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                replyPostListener?.onEnd()
                replyPostListener?.onFailure(e?.message!!)
            }
        }

    }
    fun geReplyAgain(header:String,commentId:Int) {

        Coroutines.main {
            try {
                replyPost= ReplyPost(commentId)
                Log.e("Search", "Search" + Gson().toJson(replyPost))
                val response = repository.getReply(header,replyPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))

                succeslistener?.onShow()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }

    fun getStoreCount(header:String) {

        Coroutines.main {
            try {
                Log.e("Search", "Search" + Gson().toJson(replyPost))
                val response = repository.getStoreCount(header)
                Log.e("Search", "Search" + Gson().toJson(response))
                storeCountListener?.onStore(response.data!!)

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun getLasFive(header:String) {

        Coroutines.main {
            try {
                Log.e("Search", "Search" + Gson().toJson(replyPost))
                val response = repository.getLasFive(header)
                Log.e("Search", "Search" + Gson().toJson(response))
                lastFiveSalesListener?.onLast(response.data!!)

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun getCustomerOrderCount(header:String) {

        Coroutines.main {
            try {
                Log.e("Search", "Search" + Gson().toJson(replyPost))
                val response = repository.getCustomerOrderCount(header)
                Log.e("Search", "Search" + Gson().toJson(response))
                customerOrderCountListener?.onCount(response.data!!)

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }

    fun createToken(header:String,type:Int,data:String) {

        Coroutines.main {
            try {
                tokenPost= TokenPost(type,data)
                Log.e("createToken", "createToken" + Gson().toJson(tokenPost))
                val response = repository.createToken(header,tokenPost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun sendPush(header:String,pushPost: PushPost) {
        Coroutines.main {
            try {
                Log.e("response", "response" + Gson().toJson(pushPost))
                val response = repository.sendPush(header,pushPost!!)
                Log.e("response", "response" + Gson().toJson(response))
            } catch (e: ApiException) {
                Log.e("response", "response" + e?.message)
            } catch (e: NoInternetException) {

            }
        }

    }
    fun getToken(header:String,type:Int,data:String) {

        Coroutines.main {
            try {
                tokenPost= TokenPost(type,data)
                Log.e("createToken", "createToken" + Gson().toJson(tokenPost))
                val response = repository.getToken(header,tokenPost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))
                pushListener?.onLoad(response.data!!)
            } catch (e: ApiException) {
                Log.e("createToken", "createToken" +e?.message)
            } catch (e: NoInternetException) {

            }
        }

    }
    fun updateShopUser(header:String,name:String,picture:String,address:String) {

        Coroutines.main {
            try {
                userUpdatePost= UserUpdatePost(name,picture,address)
                Log.e("createToken", "createToken" + Gson().toJson(userUpdatePost))
                val response = repository.updateShopUser(header,userUpdatePost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))
                profileUpdateListener?.onLoad(response?.message!!)
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun updatePassword(header:String,password:String) {
        changePasswordListener?.onStarted()
        Coroutines.main {
            try {
                passwordPost= PasswordPost(password!!)
                Log.e("Search", "Search" + Gson().toJson(passwordPost))
                val response = repository.updatePassword(header,passwordPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                changePasswordListener?.onUser(response?.message!!)
                changePasswordListener?.onEnd()

            } catch (e: ApiException) {
                changePasswordListener?.onEnd()
                changePasswordListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                changePasswordListener?.onEnd()
                changePasswordListener?.onFailure(e?.message!!)
            }
        }

    }
    fun createFirebaseId(header:String,type:Int,data:String) {

        Coroutines.main {
            try {
                tokenPost= TokenPost(type,data)
                Log.e("createToken", "createToken" + Gson().toJson(tokenPost))
                val response = repository.createFirebaseId(header,tokenPost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun getFirebaseId(header:String,type:Int,data:String) {

        Coroutines.main {
            try {
                tokenPost= TokenPost(type,data)
                Log.e("createToken", "createToken" + Gson().toJson(tokenPost))
                val response = repository.getFirebaseId(header,tokenPost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))
                pushListener?.onLoad(response.data!!)
            } catch (e: ApiException) {
                Log.e("createToken", "createToken" +e?.message)
            } catch (e: NoInternetException) {

            }
        }

    }
    fun getChatCount(header:String) {
        Coroutines.main {
            try {

                val response = repository.getChatCount(header!!)
                Log.e("response", "response" + Gson().toJson(response))
                chatCountListener?.onCount(response?.count!!)
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun updateChatCount(header:String,customerId:Int) {
        Coroutines.main {
            try {
                customerIdPost= CustomerIdPost(customerId)
                val response = repository.updateChatCount(header!!,customerIdPost!!)
                Log.e("response", "response" + Gson().toJson(response))
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun getDeliveryCharge(header:String) {

        Coroutines.main {
            try {

                val response = repository.getDeliveryCharge(header)
                deliveryChargeListener?.onAmount(response?.data!!)

                Log.e("deliveryCharge", "response" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun getCustomerOrdersList(token:String,id: Int) {
        customerOrderForListener?.onStarted()
        Coroutines.main {
            try {
                customerOrderPost= CustomerOrderPost(id)
                val authResponse = repository.getCustomerOrdersList(token,customerOrderPost!!)
                customerOrderForListener?.order(authResponse?.data!!)
                Log.e("getCustomerOrders", "getCustomerOrders" + Gson().toJson(authResponse))
                customerOrderForListener?.onEnd()
            } catch (e: ApiException) {
                customerOrderForListener?.onEnd()
            } catch (e: NoInternetException) {
                customerOrderForListener?.onEnd()
            }
        }

    }

    fun getCustomerOrderInformation(header: String, orderId: Int) {

        Coroutines.main {
            try {
                customerOrderPost = CustomerOrderPost(orderId)
                Log.e("Search", "Search" + Gson().toJson(customerOrderPost))
                val response = repository.getCustomerOrderInformation(header, customerOrderPost!!)
                Log.e("OrderInformation", "OrderInformation" + Gson().toJson(response))
                if (response.data != null) {
                    customerOrderInformationListener?.onShow(response?.data!!)
                } else {
                    customerOrderInformationListener?.onEmpty()
                }
                //   customerOrderListener?.onShow(response?.data!!)
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun updateReturnOrderStatus(header:String,orderId:Int,status:Int,reason:String) {

        Coroutines.main {
            try {
                orderReasonStatusPost= OrderReasonStatusPost(orderId,status,reason)
                Log.e("createToken", "createToken" + Gson().toJson(orderReasonStatusPost))
                val response = repository.updateReturnOrderStatus(header,orderReasonStatusPost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
}