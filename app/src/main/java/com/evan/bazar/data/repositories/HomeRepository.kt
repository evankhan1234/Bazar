package com.evan.bazar.data.repositories

import com.evan.bazar.data.db.AppDatabase
import com.evan.bazar.data.db.entities.User
import com.evan.bazar.data.network.MyApi
import com.evan.bazar.data.network.SafeApiRequest
import com.evan.bazar.data.network.post.*
import com.evan.bazar.data.network.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class HomeRepository (
    private val api: MyApi,
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
}