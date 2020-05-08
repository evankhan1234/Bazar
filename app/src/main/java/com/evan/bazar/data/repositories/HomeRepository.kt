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
}