package com.evan.bazar.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.evan.bazar.data.network.post.*
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.ui.home.category.ICategoryListener
import com.evan.bazar.ui.home.category.ICreateCategoryListener
import com.evan.bazar.ui.home.delivery.ICustomerOrderListListener
import com.evan.bazar.ui.home.order.IOrdersListListener
import com.evan.bazar.ui.home.product.ICategoryTypeListener
import com.evan.bazar.ui.home.product.ICreateProductListener
import com.evan.bazar.ui.home.product.ISupplierListener
import com.evan.bazar.ui.home.purchase.ICreatePurchaseListener
import com.evan.bazar.ui.home.purchase.IUnitListener
import com.evan.bazar.ui.home.settings.IShopUserListener
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
    var unitListener: IUnitListener?=null
    var supplierListener: ISupplierListener?=null
    var categoryTypeListener: ICategoryTypeListener?=null
    var orderListListener: IOrdersListListener?=null
    var shopUserListener: IShopUserListener?=null
    var typePost: CategoryType? = null
    var supplierPost: SupplierPost? = null
    var purchasePost: PurchasePost? = null
    var productPost: ProductPost? = null
    var productUpdatePost: ProductUpdatePost? = null
    var purchaseUpdatePost: PurchaseUpdatePost? = null
    var supplierUpdatePost: SupplierUpdatePost? = null
    var customerOrderListener: ICustomerOrderListListener? = null
    var typeUpdatePost: CategoryUpdate? = null
    var customerOrderPost: CustomerOrderPost? = null

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
    fun postProduct(header:String,name:String,details:String,productCode:String,productImage:String,unitId:Int,sellPrice:Double,supplierPrice:Double,supplierId:Int,shopId:Int,stock:Int,discount:Double,created:String,categoryId:Int,status:Int) {
        createProductListener?.started()
        Coroutines.main {
            try {
                productPost = ProductPost(name!!,details!!,productCode!!,productImage!!,unitId!!,sellPrice!!,supplierPrice!!, supplierId!!,shopId!!, stock!!,discount!!, created!!,categoryId!!,status!!)
                Log.e("response", "response" + Gson().toJson(productPost))
                val response = repository.postProduct(header,productPost!!)
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
    fun postUpdateProduct(header:String,id:Int,name:String,details:String,productCode:String,productImage:String,unitId:Int,sellPrice:Double,supplierPrice:Double,supplierId:Int,shopId:Int,stock:Int,discount:Double,created:String,categoryId:Int,status:Int) {
        createProductListener?.started()
        Coroutines.main {
            try {
                productUpdatePost = ProductUpdatePost(id!!,name!!,details!!,productCode!!,productImage!!,unitId!!,sellPrice!!,supplierPrice!!, supplierId!!,shopId!!, stock!!,discount!!, created!!,categoryId!!,status!!)
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
}