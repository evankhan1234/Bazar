package com.evan.bazar.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton
import com.bumptech.glide.Glide

import com.evan.bazar.R
import com.evan.bazar.ui.auth.CreateAccountActivity
import de.hdodenhof.circleimageview.CircleImageView

class StepOneFragment : Fragment() {

    var root:View?=null
    var img_user_add: AppCompatImageButton?=null
    var img_user_profile: CircleImageView?=null
    var et_name:EditText?=null
    var et_mobile:EditText?=null
    var et_email:EditText?=null
    var et_password:EditText?=null
    var et_address:EditText?=null
    var image_address:String?=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_step_one, container, false)
        et_name=root?.findViewById(R.id.et_name)
        et_mobile=root?.findViewById(R.id.et_mobile)
        et_email=root?.findViewById(R.id.et_email)
        et_password=root?.findViewById(R.id.et_password)
        et_address=root?.findViewById(R.id.et_address)
        img_user_add=root?.findViewById(R.id.img_user_add)
        img_user_profile=root?.findViewById(R.id.img_user_profile)
        img_user_add?.setOnClickListener{
            (activity as CreateAccountActivity?)!!.openImageChooser()
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
        (activity as CreateAccountActivity?)!!.stepOneValue(name,mobile,email,password,address,image_address!!)
    }
    fun showImage(temp:String?){
        image_address="http://192.168.0.106/"+temp
        Log.e("for","Image"+temp)
        Glide.with(this)
            .load("http://192.168.0.106/"+temp)
            .into(img_user_profile!!)
    }
}
