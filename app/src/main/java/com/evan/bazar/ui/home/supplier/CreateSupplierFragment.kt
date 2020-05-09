package com.evan.bazar.ui.home.supplier

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import com.evan.bazar.R
import com.evan.bazar.ui.auth.CreateAccountActivity
import com.evan.bazar.ui.home.HomeActivity
import com.evan.bazar.ui.home.HomeViewModel
import com.evan.bazar.ui.home.HomeViewModelFactory
import com.evan.bazar.util.SharedPreferenceUtil
import com.evan.bazar.util.hide
import com.evan.bazar.util.show
import com.evan.bazar.util.snackbar
import de.hdodenhof.circleimageview.CircleImageView
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*


class CreateSupplierFragment : Fragment(),KodeinAware,ICreateSupplierListener {

    override val kodein by kodein()

    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var image_address:String?=""
    var img_user_profile: CircleImageView?=null
    var progress_bar: ProgressBar?=null
    var root_layout: RelativeLayout?=null
    var img_user_add: AppCompatImageButton?=null
    var et_name: EditText?=null
    var et_mobile: EditText?=null
    var et_email: EditText?=null
    var et_address: EditText?=null
    var et_details: EditText?=null
    var switch_status: SwitchCompat?=null
    var btn_ok: Button?=null
    var status:Int?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_create_supplier, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.createSupplierListener=this
        progress_bar=root?.findViewById(R.id.progress_bar)
        root_layout=root?.findViewById(R.id.root_layout)
        btn_ok=root?.findViewById(R.id.btn_ok)
        switch_status=root?.findViewById(R.id.switch_status)
        et_name=root?.findViewById(R.id.et_name)
        et_mobile=root?.findViewById(R.id.et_mobile)
        et_email=root?.findViewById(R.id.et_email)
        et_address=root?.findViewById(R.id.et_address)
        et_details=root?.findViewById(R.id.et_details)
        img_user_profile=root?.findViewById(R.id.img_user_profile)
        img_user_add=root?.findViewById(R.id.img_user_add)
        img_user_add?.setOnClickListener{
            (activity as HomeActivity?)!!.openImageChooser()
        }
        btn_ok?.setOnClickListener {
            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val currentDate = sdf.format(Date())
            if (switch_status?.isChecked!!){
                status=1
            }
            else{
                status=0
            }
//            Log.e("currentDate","currentDate"+currentDate)
//            Log.e("status","status"+status)
//            Log.e("name","name"+et_name?.text.toString())
//            Log.e("name","name"+et_mobile?.text.toString())
//            Log.e("name","name"+et_email?.text.toString())
//            Log.e("name","name"+et_address?.text.toString())
//            Log.e("name","name"+et_details?.text.toString())
//            Log.e("name","name"+ SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_ID))

            var name: String=""
            var mobile: String=""
            var email: String=""
            var address: String=""
            var details: String=""
            name=et_name?.text.toString()
            mobile=et_mobile?.text.toString()
            email=et_email?.text.toString()
            details=et_details?.text.toString()
            address=et_address?.text.toString()
            if(name.isNullOrEmpty() && mobile.isNullOrEmpty()&& email.isNullOrEmpty() && details.isNullOrEmpty() && address.isNullOrEmpty()&& image_address.isNullOrEmpty()){
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
            else if(details.isNullOrEmpty()){
                root_layout?.snackbar("Details is Empty")
            }
            else if(address.isNullOrEmpty()){
                root_layout?.snackbar("Address is Empty")
            }
            else if(image_address.isNullOrEmpty()){
                root_layout?.snackbar("Image is Empty")
            }
            else{
                Log.e("Evan","Evan")
                viewModel.postSupplier(
                    SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,name,mobile,email,address,details,image_address!!,status!!,
                    SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),currentDate)
            }

        }
        return root
    }

    fun showImage(temp:String?){
        image_address="http://192.168.0.106/"+temp
        Log.e("for","Image"+temp)
        Glide.with(this)
            .load("http://192.168.0.106/"+temp)
            .into(img_user_profile!!)
    }

    override fun show(value: String) {
        Toast.makeText(activity, value, Toast.LENGTH_LONG).show()
        if (activity is HomeActivity) {
            (activity as HomeActivity).backPress()
        }
    }

    override fun started() {
        progress_bar?.show()
    }

    override fun end() {
        progress_bar?.hide()
    }
}
