package com.evan.bazar.ui.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.ShopType
import com.evan.bazar.ui.auth.AuthViewModel
import com.evan.bazar.ui.auth.AuthViewModelFactory
import com.evan.bazar.ui.auth.CreateAccountActivity
import com.evan.bazar.ui.interfaces.ShopTypeInterface
import com.evan.bazar.util.snackbar
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.collections.ArrayList


class StepTwoFragment : Fragment(), KodeinAware, ShopTypeInterface {
    override val kodein by kodein()
    var root: View? = null
    var spinner_shop_type: Spinner? = null
    private lateinit var viewModel: AuthViewModel
    private val factory: AuthViewModelFactory by instance()
    var et_date: EditText? = null
    var et_shop_name: EditText? = null
    var et_shop_address: EditText? = null
    var et_license_number: EditText? = null
    var id_: String? = null
    var calender: Calendar? = null
    var shopTypeDataAdapter: ArrayAdapter<String>? = null
    var shopType: MutableList<ShopType>? = null
    var root_layout: RelativeLayout?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_step_two, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        root_layout = root?.findViewById(R.id.root_layout)
        et_date = root?.findViewById(R.id.et_date)
        et_shop_name = root?.findViewById(R.id.et_shop_name)
        et_shop_address = root?.findViewById(R.id.et_shop_address)
        et_license_number = root?.findViewById(R.id.et_license_number)
        spinner_shop_type = root?.findViewById(R.id.spinner_shop_type)
        et_date?.setOnClickListener {
            DatePicker(0)
        }
        calender = Calendar.getInstance()
        year = calender!!.get(Calendar.YEAR)
        month = calender!!.get(Calendar.MONTH)
        day = calender!!.get(Calendar.DAY_OF_MONTH)

        viewModel.shopTypeListener = this
        viewModel.getShopType()
        val args: Bundle? = arguments
        if (args != null) {
            var id: String = ""
            var name: String = ""
            var date: String = ""
            var address: String = ""
            var license: String = ""
            id = args.getString("id", null)
            name = args.getString("name", null)
            date = args.getString("date", null)
            address = args.getString("address", null)
            license = args.getString("license", null)
            Log.e("evan", "value" + id)
            et_shop_name?.setText(name)
            et_date?.setText(date)
            et_shop_address?.setText(address)
            et_license_number?.setText(license)
            Handler().postDelayed({

                try {
                    for (i in shopType!!.indices) {
                        if (shopType!!.get(i).Id.equals(id)) {
                            spinner_shop_type?.setSelection(i)
                        }
                    }
                } catch (e: Exception) {
                }


            }, 200)
//
        }
        return root
    }

    fun value() {
        var date: String = ""
        var name: String = ""
        var license: String = ""
        var shopAddress: String = ""
        date = et_date?.text.toString()
        name = et_shop_name?.text.toString()
        license = et_license_number?.text.toString()
        shopAddress = et_shop_address?.text.toString()
        if(name.isNullOrEmpty() && date.isNullOrEmpty()&& license.isNullOrEmpty() && shopAddress.isNullOrEmpty() ){
            root_layout?.snackbar("All Field is Empty")
        }
        else if(name.isNullOrEmpty()){
            root_layout?.snackbar("Name is Empty")
        }
        else if(date.isNullOrEmpty()){
            root_layout?.snackbar("Agreement Date is Empty")
        }
        else if(license.isNullOrEmpty()){
            root_layout?.snackbar("License is Empty")
        }
        else if(shopAddress.isNullOrEmpty()){
            root_layout?.snackbar("Shop Address is Empty")
        }
        else{
            (activity as CreateAccountActivity?)!!.stepTwoValue(id_!!, date, name, shopAddress, license)
        }
    }

    override fun shopType(shop: MutableList<ShopType>?) {
        shopType = shop
        shop?.let {
            var mListCompanyNameList: ArrayList<String>? = ArrayList<String>()
            for (company in shop) {
                mListCompanyNameList?.add(company?.Name!!)
            }
            shopTypeDataAdapter = ArrayAdapter<String>(
                activity as Activity,
                android.R.layout.simple_spinner_item,
                mListCompanyNameList!!
            )
            shopTypeDataAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_shop_type?.adapter = shopTypeDataAdapter
            spinner_shop_type?.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        id_ = shop.get(position).Id
                        Log.e("shop", "shop" + shop.get(position).Id)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Another interface callback
                        Log.e("sdf", "S")
                    }

                }
        }

    }

    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    var birthday: String = ""
    private fun DatePicker(type: Int) {
        // Get Current Time

        val dpd = DatePickerDialog(
            activity as Activity,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                if (type == 0) {
                    this.year = year
                    month = monthOfYear
                    day = dayOfMonth
                    birthday = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
                    et_date?.setText(birthday!!)


                }

            },
            year,
            month,
            day
        )

        dpd.show()
    }

    fun removeChild() {
        val f =
            childFragmentManager.findFragmentByTag(StepTwoFragment::class.java.simpleName)
        val f1 = childFragmentManager.findFragmentByTag(StepOneFragment::class.java.simpleName)
        if (f != null) {
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right)
            transaction.remove(f)
            transaction.commit()
            childFragmentManager.popBackStack()
        }
    }


}
