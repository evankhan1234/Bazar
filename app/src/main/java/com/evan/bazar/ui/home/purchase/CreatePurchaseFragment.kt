package com.evan.bazar.ui.home.purchase

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import com.evan.bazar.R
import com.evan.bazar.data.db.entities.Purchase
import com.evan.bazar.data.db.entities.Supplier
import com.evan.bazar.data.db.entities.Unit
import com.evan.bazar.ui.home.HomeActivity
import com.evan.bazar.ui.home.HomeViewModel
import com.evan.bazar.ui.home.HomeViewModelFactory
import com.evan.bazar.util.SharedPreferenceUtil
import com.evan.bazar.util.hide
import com.evan.bazar.util.show
import com.evan.bazar.util.snackbar
import com.google.gson.Gson
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class CreatePurchaseFragment : Fragment(),KodeinAware,ICreatePurchaseListener,IUnitListener {

    override val kodein by kodein()

    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var unitDataAdapter: ArrayAdapter<String>? = null
    var  unit:MutableList<Unit>? = null
    var spinner_unit:Spinner?=null
    var progress_bar:ProgressBar?=null
    var id_: Int? = null
    var root_layout: RelativeLayout?=null

    var et_purchase_name: EditText?=null
    var et_details: EditText?=null
    var et_purchase_no: EditText?=null
    var et_purchase_date: EditText?=null
    var et_stock: EditText?=null
    var et_item: EditText?=null
    var et_quantity: EditText?=null
    var et_rate: EditText?=null
    var et_discount: EditText?=null
    var et_total: EditText?=null
    var et_grand_total: EditText?=null
    var switch_status: SwitchCompat?=null
    var btn_ok: Button?=null
    var status:Int?=null
    var id:Int?=0
    var purchase: Purchase?=null
    var calender: Calendar? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_create_purchase, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        spinner_unit=root?.findViewById(R.id.spinner_unit)
        progress_bar=root?.findViewById(R.id.progress_bar)
        root_layout=root?.findViewById(R.id.root_layout)
        et_purchase_name=root?.findViewById(R.id.et_purchase_name)
        et_details=root?.findViewById(R.id.et_details)
        et_purchase_no=root?.findViewById(R.id.et_purchase_no)
        et_purchase_date=root?.findViewById(R.id.et_purchase_date)
        et_stock=root?.findViewById(R.id.et_stock)
        et_item=root?.findViewById(R.id.et_item)
        et_quantity=root?.findViewById(R.id.et_quantity)
        et_rate=root?.findViewById(R.id.et_rate)
        et_discount=root?.findViewById(R.id.et_discount)
        et_total=root?.findViewById(R.id.et_total)
        et_grand_total=root?.findViewById(R.id.et_grand_total)
        switch_status=root?.findViewById(R.id.switch_status)
        btn_ok=root?.findViewById(R.id.btn_ok)
        et_quantity?.addTextChangedListener(quantity)
        et_rate?.addTextChangedListener(rate)
        et_discount?.addTextChangedListener(discount)
        viewModel.unitListener=this
        viewModel.createPurchaseListener=this
        viewModel.getUnit()
        et_purchase_date?.setOnClickListener {
            DatePicker(0)
        }
        calender = Calendar.getInstance()
        year = calender!!.get(Calendar.YEAR)
        month = calender!!.get(Calendar.MONTH)
        day = calender!!.get(Calendar.DAY_OF_MONTH)
        val args: Bundle? = arguments
        if (args != null) {
            if (args?.containsKey(Purchase::class.java.getSimpleName()) != null) {
                purchase = args?.getParcelable(Purchase::class.java.getSimpleName())
                id=purchase?.Id
                et_purchase_name?.setText(purchase?.ProductName)
                et_details?.setText(purchase?.ProductDetails)
                et_purchase_no?.setText(purchase?.PurchaseNo)
                et_purchase_date?.setText(getStartDate(purchase?.PurchaseDate))
                et_stock?.setText(purchase?.Stock!!.toString())
                et_item?.setText(purchase?.Item!!.toString())
                et_rate?.setText(purchase?.Rate!!.toString())
                et_quantity?.setText(purchase?.Quantity!!.toString())
                et_discount?.setText(purchase?.Discount!!.toString())
                et_total?.setText(purchase?.Total!!.toString())
                et_grand_total?.setText(purchase?.GrandTotal!!.toString())
                switch_status?.isChecked = purchase?.Status==1
                Handler().postDelayed({

                    try {
                        Log.e("unit","unit"+ Gson().toJson(unit))
                        for (i in unit!!.indices) {
                            if (unit!!.get(i).Id!!.equals(purchase?.UnitId)) {
                                spinner_unit?.setSelection(i)
                            }
                        }
                    } catch (e: Exception) {
                    }



                }, 300)
                Log.e("data","data"+ Gson().toJson(purchase))
            }
        }

        btn_ok?.setOnClickListener {

            if (id==0){
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                if (switch_status?.isChecked!!){
                    status=1
                }
                else{
                    status=0
                }

                var purchase_name: String=""
                var purchase_details: String=""
                var purchase_no: String=""
                var purchase_date: String=""
                var stock: String=""
                var Item: String=""
                var quantity: String=""
                var rate: String=""
                var discount: String=""
                var total: String=""
                var grand_total: String=""
                purchase_name=et_purchase_name?.text.toString()
                purchase_details=et_details?.text.toString()
                purchase_no=et_purchase_no?.text.toString()
                purchase_date=et_purchase_date?.text.toString()
                stock=et_stock?.text.toString()
                Item=et_item?.text.toString()
                quantity=et_quantity?.text.toString()
                rate=et_rate?.text.toString()
                discount=et_discount?.text.toString()
                total=et_total?.text.toString()
                grand_total=et_grand_total?.text.toString()
                if(purchase_name.isNullOrEmpty() && purchase_details.isNullOrEmpty()&& purchase_no.isNullOrEmpty() && purchase_date.isNullOrEmpty() && stock.isNullOrEmpty()&& Item.isNullOrEmpty()&& quantity.isNullOrEmpty()&& discount.isNullOrEmpty()&& rate.isNullOrEmpty()&& total.isNullOrEmpty()&& grand_total.isNullOrEmpty()){
                    root_layout?.snackbar("All Field is Empty")
                }
                else if(purchase_name.isNullOrEmpty()){
                    root_layout?.snackbar("Purchase Name is Empty")
                }
                else if(purchase_details.isNullOrEmpty()){
                    root_layout?.snackbar("Purchase Details is Empty")
                }
                else if(purchase_no.isNullOrEmpty()){
                    root_layout?.snackbar("Purchase No is Empty")
                }
                else if(purchase_date.isNullOrEmpty()){
                    root_layout?.snackbar("Purchase Date is Empty")
                }
                else if(stock.isNullOrEmpty()){
                    root_layout?.snackbar("Stock is Empty")
                }
                else if(Item.isNullOrEmpty()){
                    root_layout?.snackbar("Item is Empty")
                }
                else if(quantity.isNullOrEmpty()){
                    root_layout?.snackbar("Quantity is Empty")
                }
                else if(rate.isNullOrEmpty()){
                    root_layout?.snackbar("Rate is Empty")
                }
                else if(discount.isNullOrEmpty()){
                    root_layout?.snackbar("Discount is Empty")
                }
                else if(total.isNullOrEmpty()){
                    root_layout?.snackbar("Total is Empty")
                }
                else if(grand_total.isNullOrEmpty()){
                    root_layout?.snackbar("Grand Total is Empty")
                }
                else{
                    Log.e("Evan","Evan")

                    viewModel.postPurchase(SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,purchase_name!!,purchase_details!!,purchase_no!!,purchase_date!!,
                        stock!!.toInt(),Item!!.toInt(),quantity!!.toInt(),rate!!.toDouble(),discount!!.toDouble(),total!!.toDouble(),grand_total!!.toDouble(),id_!!,SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),
                        currentDate!!,status!!)

//                    viewModel.postSupplier(
//                        SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,name,mobile,email,address,details,image_address!!,status!!,
//                        SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),currentDate)
                }
            }
            else{
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                if (switch_status?.isChecked!!){
                    status=1
                }
                else{
                    status=0
                }

                var purchase_name: String=""
                var purchase_details: String=""
                var purchase_no: String=""
                var purchase_date: String=""
                var stock: String=""
                var Item: String=""
                var quantity: String=""
                var rate: String=""
                var discount: String=""
                var total: String=""
                var grand_total: String=""
                purchase_name=et_purchase_name?.text.toString()
                purchase_details=et_details?.text.toString()
                purchase_no=et_purchase_no?.text.toString()
                purchase_date=et_purchase_date?.text.toString()
                stock=et_stock?.text.toString()
                Item=et_item?.text.toString()
                quantity=et_quantity?.text.toString()
                rate=et_rate?.text.toString()
                discount=et_discount?.text.toString()
                total=et_total?.text.toString()
                grand_total=et_grand_total?.text.toString()
                if(purchase_name.isNullOrEmpty() && purchase_details.isNullOrEmpty()&& purchase_no.isNullOrEmpty() && purchase_date.isNullOrEmpty() && stock.isNullOrEmpty()&& Item.isNullOrEmpty()&& quantity.isNullOrEmpty()&& discount.isNullOrEmpty()&& rate.isNullOrEmpty()&& total.isNullOrEmpty()&& grand_total.isNullOrEmpty()){
                    root_layout?.snackbar("All Field is Empty")
                }
                else if(purchase_name.isNullOrEmpty()){
                    root_layout?.snackbar("Purchase Name is Empty")
                }
                else if(purchase_details.isNullOrEmpty()){
                    root_layout?.snackbar("Purchase Details is Empty")
                }
                else if(purchase_no.isNullOrEmpty()){
                    root_layout?.snackbar("Purchase No is Empty")
                }
                else if(purchase_date.isNullOrEmpty()){
                    root_layout?.snackbar("Purchase Date is Empty")
                }
                else if(stock.isNullOrEmpty()){
                    root_layout?.snackbar("Stock is Empty")
                }
                else if(Item.isNullOrEmpty()){
                    root_layout?.snackbar("Item is Empty")
                }
                else if(quantity.isNullOrEmpty()){
                    root_layout?.snackbar("Quantity is Empty")
                }
                else if(rate.isNullOrEmpty()){
                    root_layout?.snackbar("Rate is Empty")
                }
                else if(discount.isNullOrEmpty()){
                    root_layout?.snackbar("Discount is Empty")
                }
                else if(total.isNullOrEmpty()){
                    root_layout?.snackbar("Total is Empty")
                }
                else if(grand_total.isNullOrEmpty()){
                    root_layout?.snackbar("Grand Total is Empty")
                }
                else{
                    Log.e("Evan","Evan")
                    viewModel.updatePurchase(SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,id!!,purchase_name!!,purchase_details!!,purchase_no!!,purchase_date!!,
                        stock!!.toInt(),Item!!.toInt(),quantity!!.toInt(),rate!!.toDouble(),discount!!.toDouble(),total!!.toDouble(),grand_total!!.toDouble(),id_!!,SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),
                        currentDate!!,status!!)
//                    viewModel.postUpdateSupplier(
//                        SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,id!!,name,mobile,email,address,details,image_address!!,status!!,
//                SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),currentDate)
            }
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
    override fun unit(units: MutableList<Unit>?) {
        unit = units
        units?.let {
            var mListUnitList: ArrayList<String>? = ArrayList<String>()
            for (unit in units) {
                mListUnitList?.add(unit?.Name!!)
            }
            unitDataAdapter = ArrayAdapter<String>(
                activity as Activity,
                android.R.layout.simple_spinner_item,
                mListUnitList!!
            )
            unitDataAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_unit?.adapter = unitDataAdapter
            spinner_unit?.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        id_ = units.get(position).Id
                        Log.e("shop", "shop" + units.get(position).Id)
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
                    et_purchase_date?.setText(birthday!!)


                }

            },
            year,
            month,
            day
        )

        dpd.show()
    }
    fun getStartDate(startDate: String?): String? {
        val inputFormat =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val outputFormat =
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(startDate, inputFormat).format(outputFormat)
    }
    var quantity: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            try {
                var quantity:Int?=0
                var rate:Double?=0.0
                var discount:Double?=0.0
                var total:Double?=0.0
                var grand_total:Double?=0.0
                quantity=s.toString().toInt()
                rate=et_rate?.text.toString().toDouble()
                discount=et_discount?.text.toString().toDouble()
                total=quantity*rate
                grand_total=total-discount
                et_total?.setText(total?.toString())
                et_grand_total?.setText(grand_total?.toString())
            } catch (e: Exception) {


                try {
                    var quantity:Int?=0
                    var rate:Double?=0.0
                    var total:Double?=0.0
                    quantity=s.toString().toInt()
                    rate=et_rate?.text.toString().toDouble()
                    total=quantity*rate
                    et_total?.setText(total?.toString())
                } catch (e: Exception) {
                }
            }


        }

    }
    var rate: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            try {
                var quantity:Int?=0
                var rate:Double?=0.0
                var discount:Double?=0.0
                var total:Double?=0.0
                var grand_total:Double?=0.0
                quantity=et_quantity?.text.toString()?.toInt()
                rate=s.toString().toDouble()
                discount=et_discount?.text.toString().toDouble()
                total=quantity!!*rate
                grand_total=total-discount
                et_total?.setText(total?.toString())
                et_grand_total?.setText(grand_total?.toString())
            } catch (e: Exception) {
                try {
                    var quantity:Int?=0
                    var rate:Double?=0.0
                    var total:Double?=0.0
                    quantity=et_quantity?.text.toString()?.toInt()
                    rate=s.toString().toDouble()
                    total=quantity!!*rate
                    et_total?.setText(total?.toString())
                } catch (e: Exception) {
                }

            }


        }

    }
    var discount: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            try {
                var quantity:Int?=0
                var rate:Double?=0.0
                var discount:Double?=0.0
                var total:Double?=0.0
                var grand_total:Double?=0.0
                quantity=et_quantity?.text.toString()?.toInt()
                rate=et_rate?.text.toString()?.toDouble()
                discount=s.toString().toDouble()
                total=quantity!!*rate!!
                grand_total=total-discount
                et_total?.setText(total?.toString())
                et_grand_total?.setText(grand_total?.toString())
            } catch (e: Exception) {

            }


        }

    }
}
