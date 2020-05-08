package com.evan.bazar.ui.home.category

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProviders

import com.evan.bazar.R
import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.ui.home.HomeActivity
import com.evan.bazar.ui.home.HomeViewModel
import com.evan.bazar.ui.home.HomeViewModelFactory
import com.evan.bazar.util.SharedPreferenceUtil
import com.evan.bazar.util.hide
import com.evan.bazar.util.show
import com.google.gson.Gson
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*


class CreateCategoryFragment : Fragment(),KodeinAware,ICreateCategoryListener {

    override val kodein by kodein()

    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var et_name:EditText?=null
    var progress_bar:ProgressBar?=null
    var switch_status:SwitchCompat?=null
    var btn_ok:Button?=null
    var status:Int?=null
    var id:Int?=0
    var categoryType:CategoryType?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_create_category, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.createCategoryListener=this
        et_name=root?.findViewById(R.id.et_name)
        switch_status=root?.findViewById(R.id.switch_status)
        progress_bar=root?.findViewById(R.id.progress_bar)
        btn_ok=root?.findViewById(R.id.btn_ok)
        val args: Bundle? = arguments
        if (args != null) {
            if (args?.containsKey(CategoryType::class.java.getSimpleName()) != null) {
                categoryType = args?.getParcelable(CategoryType::class.java.getSimpleName())
                id=categoryType?.Id?.toInt()
                et_name?.setText(categoryType?.Name)
                switch_status?.isChecked = categoryType?.Status==1
                Log.e("data","data"+Gson().toJson(categoryType))
            }
        }

        if (id==0){
            btn_ok?.setOnClickListener {
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                if (switch_status?.isChecked!!){
                    status=1
                }
                else{
                    status=0
                }
                Log.e("Evan","Evan")
                Log.e("currentDate","currentDate"+currentDate)
                Log.e("status","status"+status)
                Log.e("name","name"+et_name?.text.toString())
                Log.e("name","name"+ SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_ID))
                viewModel.postCategoryType(SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,et_name?.text.toString(),status!!,SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),currentDate)
            }
        }
        else{
            btn_ok?.setOnClickListener {
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                if (switch_status?.isChecked!!){
                    status=1
                }
                else{
                    status=0
                }
                Log.e("Khan","Khan"+id)
                Log.e("currentDate","currentDate"+currentDate)
                Log.e("status","status"+status)
                Log.e("name","name"+et_name?.text.toString())
                Log.e("name","name"+ SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_ID))
                viewModel.postUpdateCategoryType(SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,id!!,et_name?.text.toString(),status!!,SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),currentDate)
            }
        }



        return root
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
