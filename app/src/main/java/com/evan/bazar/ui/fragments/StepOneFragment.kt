package com.evan.bazar.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageButton
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import com.evan.bazar.R
import com.evan.bazar.ui.auth.CreateAccountActivity
import com.evan.bazar.util.MyPasswordTransformationMethod
import com.evan.bazar.util.snackbar
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_login.*

class StepOneFragment : Fragment() {

    var root:View?=null
    var root_layout:RelativeLayout?=null
    var img_user_add: AppCompatImageButton?=null
    var img_user_profile: CircleImageView?=null
    var et_name:EditText?=null
    var et_mobile:EditText?=null
    var et_email:EditText?=null
    var et_password:EditText?=null
    var et_address:EditText?=null
    var show_pass:ImageView?=null
    var image_address:String?=""
    var progress_bar: ProgressBar?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_step_one, container, false)
        root_layout=root?.findViewById(R.id.root_layout)
        et_name=root?.findViewById(R.id.et_name)
        show_pass=root?.findViewById(R.id.show_pass)
        et_mobile=root?.findViewById(R.id.et_mobile)
        et_email=root?.findViewById(R.id.et_email)
        et_password=root?.findViewById(R.id.et_password)
        et_address=root?.findViewById(R.id.et_address)
        img_user_add=root?.findViewById(R.id.img_user_add)
        img_user_profile=root?.findViewById(R.id.img_user_profile)
        progress_bar=root?.findViewById(R.id.progress_bar)
        img_user_add?.setOnClickListener{
            (activity as CreateAccountActivity?)!!.openImageChooser()
        }
        et_password?.transformationMethod = MyPasswordTransformationMethod()
        show_pass?.setOnClickListener {
            onPasswordVisibleOrInvisible()
        }
        val args: Bundle? = arguments
        if (args != null) {
            var name: String=""
            var mobile: String=""
            var email: String=""
            var password: String=""
            var address: String=""
            var image: String=""
            name = args.getString("name", null)
            mobile = args.getString("mobile", null)
            email = args.getString("email", null)
            password = args.getString("password", null)
            address = args.getString("address", null)
            image = args.getString("image", null)
            et_name?.setText(name)
            et_mobile?.setText(mobile)
            et_email?.setText(email)
            et_password?.setText(password)
            et_address?.setText(address)
            image_address=image
            Glide.with(this)
                .load(image)
                .into(img_user_profile!!)
        }

        return root
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
    fun value(){
        var name: String=""
        var mobile: String=""
        var email: String=""
        var password: String=""
        var address: String=""
        name=et_name?.text.toString()
        mobile=et_mobile?.text.toString()
        email=et_email?.text.toString()
        password=et_password?.text.toString()
        address=et_address?.text.toString()
        if(name.isNullOrEmpty() && mobile.isNullOrEmpty()&& email.isNullOrEmpty() && password.isNullOrEmpty() && address.isNullOrEmpty()&& image_address.isNullOrEmpty()){
            root_layout?.snackbar("All Field is Empty")
        }
        else if(name.isNullOrEmpty()){
            root_layout?.snackbar("Name is Empty")
        }
        else if(mobile.isNullOrEmpty()){
            root_layout?.snackbar("Mobile is Empty")
        }
        else if(email.isNullOrEmpty()){
            root_layout?.snackbar("Email is Empty")
        }
        else if(password.isNullOrEmpty()){
            root_layout?.snackbar("Password is Empty")
        }
        else if(address.isNullOrEmpty()){
            root_layout?.snackbar("Address is Empty")
        }
        else if(image_address.isNullOrEmpty()){
            root_layout?.snackbar("Image is Empty")
        }
        else{
            (activity as CreateAccountActivity?)!!.stepOneValue(name,mobile,email,password,address,image_address!!)
        }

    }
    fun showImage(temp:String?){
//        image_address="http://hathbazzar.com/"+temp
//        Log.e("for","Image"+temp)
//        Glide.with(this)
//            .load("http://hathbazzar.com/"+temp)
//            .into(img_user_profile!!)
        image_address="http://199.192.28.11/"+temp
        Log.e("","http://199.192.28.11/"+temp)
//        Glide.with(this)
//            .load("http://199.192.28.11/"+temp)
//            .into(img_user_profile!!)
        loadImage(image_address!!)

    }
    fun loadImage(image_path:String){
        progress_bar?.visibility = View.VISIBLE
        Glide.with(this)
            .load(image_path)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progress_bar?.visibility = View.GONE
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progress_bar?.visibility = View.GONE
                    return false
                }
            })
            .into(img_user_profile!!)
    }
}
