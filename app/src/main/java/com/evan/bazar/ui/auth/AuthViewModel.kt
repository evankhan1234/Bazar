package com.evan.bazar.ui.auth

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.evan.bazar.data.network.post.AuthPost
import com.evan.bazar.data.repositories.UserRepository
import com.evan.bazar.interfaces.Listener
import com.evan.bazar.ui.interfaces.ShopTypeInterface
import com.evan.bazar.util.ApiException
import com.evan.bazar.util.Coroutines
import com.evan.bazar.util.NoInternetException
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody


class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var authPost: AuthPost? = null
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordconfirm: String? = null
    var AddListener: Listener? = null
    var authListener: AuthListener? = null
    var shopTypeListener: ShopTypeInterface? = null

    fun getLoggedInUser() = repository.getUser()


    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        Coroutines.main {
            try {
                authPost = AuthPost(email!!, password!!, "")
                val authResponse = repository.userLoginFor(authPost!!)
                Log.e("response", "response" + authResponse.message)
//                authResponse.user?.let {
//                    authListener?.onSuccess(it)
//                    repository.saveUser(it)
//                    return@main
//                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }

    }

    fun uploadImage(part: MultipartBody.Part, body: RequestBody) {
        //else success
        Coroutines.main {
            try {
                val uploadImageResponse = repository.createImage(part, body)
                if (uploadImageResponse.success!!) {
                    Log.e("imageUpload", "" + Gson().toJson(uploadImageResponse));
                    AddListener?.Success(uploadImageResponse.img_address!!)
                } else {
                    // val alerts = repository.getDeliveryistAPI(1)
                    /**Save in local db*/
                    //   repository.saveAllAlert(alerts)
                    //listOfDelivery.value = alerts
                    AddListener?.Failure(uploadImageResponse.message!!)
                    Log.e("imageUpload", "" + Gson().toJson(uploadImageResponse));

                }
            } catch (e: ApiException) {
                AddListener?.Success(e.message!!)
//                deliveryAddListener!!.onFailure(e.message!!)
                Log.e("imageUpload", "" + (e.message!!));
            } catch (e: NoInternetException) {
                AddListener?.Success(e.message!!)
//                deliveryAddListener!!.onFailure(e.message!!)
                Log.e("imageUpload", "" + (e.message!!));
            }

        }

    }

    fun onLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignup(view: View) {
        Intent(view.context, SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }


    fun onSignupButtonClick(view: View) {
        authListener?.onStarted()

        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Name is required")
            return
        }

        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email is required")
            return
        }

        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Please enter a password")
            return
        }

        if (password != passwordconfirm) {
            authListener?.onFailure("Password did not match")
            return
        }


        Coroutines.main {
            try {
                val authResponse = repository.userSignup(name!!, email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }

    }

    fun getShopType() {
        Coroutines.main {
            try {
                val authResponse = repository.getShopType()
                shopTypeListener?.shopType(authResponse?.data!!)
                Log.e("response", "response" + Gson().toJson(authResponse))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
}