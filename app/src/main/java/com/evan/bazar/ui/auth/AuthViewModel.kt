package com.evan.bazar.ui.auth

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.evan.bazar.data.network.post.AuthPost
import com.evan.bazar.data.network.post.RegistrationPost
import com.evan.bazar.data.repositories.UserRepository
import com.evan.bazar.interfaces.Listener
import com.evan.bazar.ui.interfaces.ShopTypeInterface
import com.evan.bazar.ui.interfaces.SignUpInterface
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
    var registrationPost: RegistrationPost? = null
    var name: String? = null
    var email: String? = ""
    var mobile: String? = ""
    var password: String? = null
    var passwordconfirm: String? = null
    var AddListener: Listener? = null
    var signUpInterface: SignUpInterface? = null
    var authListener: AuthListener? = null
    var shopTypeListener: ShopTypeInterface? = null

    fun getLoggedInUser() = repository.getUser()


    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if ( password.isNullOrEmpty()) {
            authListener?.onFailure("Password is Empty")
            return
        }

        Coroutines.main {
            try {
                authPost = AuthPost(email!!, password!!, mobile!!)
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

    fun signUp(email:String,password:String,mobile:String,name:String,agreementDate:String,ownerAddress:String,status:String,shopTypeId:String,picture:String,created:String,shopName:String,shopAddress:String,licenseNumber:String) {
        signUpInterface?.onStartProgress()
        Coroutines.main {
            try {
                registrationPost= RegistrationPost(email,password,mobile,name,agreementDate,ownerAddress,status,shopTypeId,picture,created,shopName,shopAddress,licenseNumber)
                val authResponse = repository.userRegistrationFor(registrationPost!!)
                Log.e("response", "response" + Gson().toJson(authResponse))
                if (authResponse.success!!) {

                    signUpInterface?.onEndProgress()
                    signUpInterface?.onSignUpSuccess(authResponse.message!!)

                } else {
                    signUpInterface?.onEndProgress()
                    signUpInterface?.onSignUpFailed(authResponse.message!!)

                }
            } catch (e: ApiException) {
                Log.e("ApiException", "" + e.message)
                signUpInterface?.onSignUpFailed(e.message!!)
                signUpInterface?.onEndProgress()
            } catch (e: NoInternetException) {
                signUpInterface?.onEndProgress()
                Log.e("NoInternetException", "" + e.message)
                signUpInterface?.onSignUpFailed(e.message!!)
                signUpInterface?.onEndProgress()

            }
        }

    }
}