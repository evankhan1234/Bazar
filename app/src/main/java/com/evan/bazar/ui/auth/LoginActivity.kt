package com.evan.bazar.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.evan.bazar.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.User
import com.evan.bazar.databinding.ActivityLoginBinding
import com.evan.bazar.util.*
import com.google.firebase.auth.FirebaseAuth
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class LoginActivity : AppCompatActivity(),KodeinAware, AuthListener  {

    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()
    var text_building_name: String? = ""
    var tv_sign_in: TextView? = null
    var et_email: EditText? = null
    var et_password: EditText? = null
    var et_mobile: EditText? = null
    var radio_email: RadioButton? = null
    var radio_mobile: RadioButton? = null
    var show_pass: ImageView? = null
    var viewModel:AuthViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
         binding.viewmodel = viewModel
        et_email=findViewById(R.id.et_email)
        et_mobile=findViewById(R.id.et_mobile)
        radio_email=findViewById(R.id.radio_email)
        tv_sign_in=findViewById(R.id.tv_sign_in)
        radio_mobile=findViewById(R.id.radio_mobile)
        show_pass=findViewById(R.id.show_pass)
        et_password=findViewById(R.id.et_password)
        radio_email?.setOnClickListener{
            et_mobile?.visibility= View.GONE
            et_email?.visibility=View.VISIBLE
            et_mobile?.setText("")
        }
        radio_mobile?.setOnClickListener{
            et_email?.visibility=View.GONE
            et_mobile?.visibility=View.VISIBLE
            et_mobile?.setText("")
        }
        tv_sign_in?.setOnClickListener{
            Intent(this, CreateAccountActivity::class.java).also {

                startActivity(it)
            }
        }
        et_password?.transformationMethod = MyPasswordTransformationMethod()
        show_pass?.setOnClickListener {
            onPasswordVisibleOrInvisible()
        }
        viewModel?.authListener = this

//        var token:String?=""
//        token = SharedPreferenceUtil.getShared(this, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
//        if (token != null && !token?.trim().equals("") && !token.isNullOrEmpty()) {
//            Intent(this, HomeActivity::class.java).also {
//                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(it)
//            }
//        } else {
//           // gotoLoginPage()
//        }
//        viewModel.getLoggedInUser().observe(this, Observer { user ->
//            if(user != null){
//                Intent(this, HomeActivity::class.java).also {
//                   it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    startActivity(it)
//                }
//            }
//        })
//        Coroutines.main {
//            viewModel.quotes.await().observe(this, Observer { user ->
//                if(user != null){
//                  //  initRecyclerView(user!!)
//                }
//            })
//        }
        text_building_name =
            resources!!.getString(R.string.account) + "<font color=#DDC915> Sign up</font>"
        tv_sign_in?.text = Html.fromHtml(text_building_name)

    }

    override fun onStarted() {
        progress_bar.show()
    }
    fun onPasswordVisibleOrInvisible() {
        val cursorPosition = et_password?.selectionStart

        if (et_password?.transformationMethod == null) {
            et_password?.transformationMethod = MyPasswordTransformationMethod()
            show_pass?.isSelected = false
        } else {

            et_password?.transformationMethod = null
            show_pass?.isSelected = true
        }
        et_password?.setSelection(cursorPosition!!)
    }
    var auth: FirebaseAuth? = null
    override fun onSuccess(user: User) {
        Log.e("response1","response1")
        SharedPreferenceUtil.saveShared(
            this,
            SharedPreferenceUtil.TYPE_NAME,
            user?.OwnerName!!
        )
        SharedPreferenceUtil.saveShared(
            this,
            SharedPreferenceUtil.TYPE_IMAGE,
            user?.Picture!!
        )
        SharedPreferenceUtil.saveShared(
            this,
            SharedPreferenceUtil.TYPE_SHOP_TYPE_ID,
            user?.ShopTypeId!!.toString()
        )
        auth!!.signInWithEmailAndPassword(user.Email!!, et_password?.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e("response2","response2")
                    progress_bar.hide()
                    Intent(this, HomeActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                    finish()
                } else {
                    Log.e("response3","response3")
                    Toast.makeText(
                        this@LoginActivity,
                        "Authentication failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

}
