package com.evan.bazar.ui.home.settings.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.ShopUser
import com.evan.bazar.ui.home.HomeActivity
import com.evan.bazar.ui.home.HomeViewModel
import com.evan.bazar.ui.home.HomeViewModelFactory
import com.evan.bazar.util.SharedPreferenceUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.HashMap


class ProfileUpdateFragment : Fragment() ,KodeinAware,IProfileUpdateListener{
    override val kodein by kodein()

    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var shopUsers: ShopUser?=null
    var name: String=""
    var address: String=""
    var img_user_add:AppCompatImageButton?=null
    var img_user_profile:ImageView?=null
    var et_name:EditText?=null
    var et_address:EditText?=null
    var et_mobile:EditText?=null
    var et_email:EditText?=null
    var btn_update:Button?=null
    var image_address:String?=""
    var token:String?=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_profile_update, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.profileUpdateListener=this
        img_user_add=root?.findViewById(R.id.img_user_add)
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        img_user_profile=root?.findViewById(R.id.img_user_profile)
        et_mobile=root?.findViewById(R.id.et_mobile)
        et_name=root?.findViewById(R.id.et_name)
        et_email=root?.findViewById(R.id.et_email)
        et_address=root?.findViewById(R.id.et_address)
        btn_update=root?.findViewById(R.id.btn_update)
        img_user_add?.setOnClickListener {
            (activity as HomeActivity?)!!.openImageChooser()
        }
        val args: Bundle? = arguments
        if (args != null) {
            if (args?.containsKey(ShopUser::class.java.getSimpleName()) != null) {
                shopUsers = args?.getParcelable(ShopUser::class.java.getSimpleName())
                image_address=shopUsers?.Picture
                name=shopUsers?.OwnerName!!
                address=shopUsers?.OwnerAddress!!
                Glide.with(context!!)
                    .load(shopUsers?.Picture)
                    .into(img_user_profile!!)
                et_name?.setText(name)
                et_address?.setText(shopUsers?.OwnerAddress!!)
                et_mobile?.setText(shopUsers?.OwnerMobileNumber!!)
                et_email?.setText(shopUsers?.Email!!)

            }

        }
        btn_update?.setOnClickListener {
            name=et_name?.text.toString()
            address=et_address?.text.toString()
            viewModel?.updateShopUser(token!!,name,image_address!!,address)
        }
        return root
    }

    fun showImage(temp:String?){
//        image_address="http://hathbazzar.com/"+temp
//        Log.e("for","Image"+temp)
//        Glide.with(this)
//            .load("http://hathbazzar.com/"+temp)
//            .into(img_user_profile!!)
      //  img_user_add?.visibility=View.INVISIBLE

        image_address="http://199.192.28.11/"+temp
        Log.e("for","Image"+temp)
        Glide.with(this)
            .load("http://199.192.28.11/"+temp)
            .into(img_user_profile!!)
    }
    var reference: DatabaseReference? = null
    override fun onLoad(message: String) {
        Toast.makeText(context!!,message, Toast.LENGTH_SHORT).show()
        (activity as HomeActivity?)!!.onBackPressed()
        status(image_address!!)
        SharedPreferenceUtil.saveShared(
            activity!!,
            SharedPreferenceUtil.TYPE_IMAGE,
            image_address!!)
    }
    open fun status(status: String) {
        var fuser: FirebaseUser? = null
        fuser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser!!.uid)
        val hashMap =
            HashMap<String, Any>()
        hashMap["imageURL"] = status
        reference!!.updateChildren(hashMap)
    }
}