package com.evan.bazar.ui.home.product

import android.app.Activity
import android.os.Bundle
import android.os.Handler
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
import com.evan.bazar.data.db.entities.*
import com.evan.bazar.data.db.entities.Unit
import com.evan.bazar.ui.home.HomeActivity
import com.evan.bazar.ui.home.HomeViewModel
import com.evan.bazar.ui.home.HomeViewModelFactory
import com.evan.bazar.ui.home.purchase.IUnitListener
import com.evan.bazar.util.SharedPreferenceUtil
import com.evan.bazar.util.hide
import com.evan.bazar.util.show
import com.evan.bazar.util.snackbar
import com.google.gson.Gson
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.fragment_create_product.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CreateProductFragment : Fragment(),KodeinAware,IUnitListener,ICategoryTypeListener,ISupplierListener,ICreateProductListener {
    override val kodein by kodein()

    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var unitDataAdapter: ArrayAdapter<String>? = null
    var supplierDataAdapter: ArrayAdapter<String>? = null
    var categoryDataAdapter: ArrayAdapter<String>? = null
    var  unit:MutableList<Unit>? = null
    var  category:MutableList<CategoryType>? = null
    var  supplier:MutableList<Supplier>? = null
    var spinner_unit: Spinner?=null
    var spinner_supplier: Spinner?=null
    var spinner_category: Spinner?=null
    var progress_bar: ProgressBar?=null
    var id_unit: Int? = null
    var id_supplier: Int? = 0
    var id_category: Int? = 0
    var root_layout: RelativeLayout?=null
    var et_product_name: EditText?=null
    var et_details: EditText?=null
    var et_product_code: EditText?=null
    var et_sell_price: EditText?=null
    var et_supplier_price: EditText?=null
    var et_discount: EditText?=null
    var et_stock: EditText?=null
    var btn_ok: Button?=null
    var token:String?=""
    var product: Product?=null
    var switch_status: SwitchCompat?=null
    var id:Int?=0
    var status:Int?=null
    var image_address:String?=""
    var img_background_mypage: RoundedImageView?=null
    var img_user_add: AppCompatImageButton?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_create_product, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.unitListener=this
        viewModel.supplierListener=this
        viewModel.categoryTypeListener=this
        viewModel.createProductListener=this
        spinner_supplier=root?.findViewById(R.id.spinner_supplier)
        spinner_category=root?.findViewById(R.id.spinner_category)
        spinner_unit=root?.findViewById(R.id.spinner_unit)
        progress_bar=root?.findViewById(R.id.progress_bar)
        root_layout=root?.findViewById(R.id.root_layout)
        et_product_name=root?.findViewById(R.id.et_product_name)
        et_details=root?.findViewById(R.id.et_details)
        et_product_code=root?.findViewById(R.id.et_product_code)
        et_sell_price=root?.findViewById(R.id.et_sell_price)
        et_supplier_price=root?.findViewById(R.id.et_supplier_price)
        et_discount=root?.findViewById(R.id.et_discount)
        et_stock=root?.findViewById(R.id.et_stock)
        btn_ok=root?.findViewById(R.id.btn_ok)
        switch_status=root?.findViewById(R.id.switch_status)
        img_background_mypage=root?.findViewById(R.id.img_image)

        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        viewModel.getUnit()
       // viewModel.getSupplier(token!!)
        viewModel.getCategory(token!!)
        img_user_add=root?.findViewById(R.id.img_user_add)
        img_user_add?.setOnClickListener{
            (activity as HomeActivity?)!!.openImageChooser()
        }
        val args: Bundle? = arguments
        if (args != null) {
            if (args?.containsKey(Product::class.java.getSimpleName()) != null) {
                product = args?.getParcelable(Product::class.java.getSimpleName())
                id=product?.Id
                et_product_name?.setText(product?.Name)
                et_details?.setText(product?.Details)
                et_product_code?.setText(product?.ProductCode)
                et_stock?.setText(product?.Stock!!.toString())
                et_sell_price?.setText(product?.SellPrice!!.toString())
                et_supplier_price?.setText(product?.SupplierPrice!!.toString())
                et_discount?.setText(product?.Discount!!.toString())
                switch_status?.isChecked = product?.Status==1
                image_address=product?.ProductImage
                Glide.with(this)
                    .load(product?.ProductImage)
                    .into(img_background_mypage!!)
                
                Log.e("data","data"+ Gson().toJson(product))
            }
        }
        btn_ok?.setOnClickListener {

            if (id == 0) {
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                if (switch_status?.isChecked!!) {
                    status = 1
                } else {
                    status = 0
                }

                var product_name: String = ""
                var product_details: String = ""
                var product_code: String = ""
                var sell_price: String = ""
                var supplier_price: String = ""
                var stock: String = ""
                var discount: String = ""
                product_name = et_product_name?.text.toString()
                product_details = et_details?.text.toString()
                product_code = et_product_code?.text.toString()
                sell_price = et_sell_price?.text.toString()
                stock = et_stock?.text.toString()
                supplier_price = et_supplier_price?.text.toString()
                discount = et_discount?.text.toString()
                if (product_name.isNullOrEmpty() && product_details.isNullOrEmpty() && product_code.isNullOrEmpty() && sell_price.isNullOrEmpty() && supplier_price.isNullOrEmpty() && stock.isNullOrEmpty() && discount.isNullOrEmpty() ) {
                    root_layout?.snackbar("All Field is Empty")
                } else if (product_name.isNullOrEmpty()) {
                    root_layout?.snackbar("Product Name is Empty")
                } else if (product_details.isNullOrEmpty()) {
                    root_layout?.snackbar("Product Details is Empty")
                } else if (product_code.isNullOrEmpty()) {
                    root_layout?.snackbar("Product Code is Empty")
                } else if (sell_price.isNullOrEmpty()) {
                    root_layout?.snackbar("Sell Price is Empty")
                } else if (supplier_price.isNullOrEmpty()) {
                    root_layout?.snackbar("Supplier Price is Empty")
                } else if (stock.isNullOrEmpty()) {
                    root_layout?.snackbar("Stock is Empty")
                }  else if (discount.isNullOrEmpty()) {
                    root_layout?.snackbar("Discount is Empty")
                }  else if (image_address.isNullOrEmpty()) {
                root_layout?.snackbar("Image is Empty")
               }

                else if (id_category==0) {
                    root_layout?.snackbar("Product Category is Empty.Please Add Product Category")
                }
                else {
                    Log.e("Evan", "Evan")

                    viewModel.postProduct(
                        SharedPreferenceUtil.getShared(
                            activity!!,
                            SharedPreferenceUtil.TYPE_AUTH_TOKEN
                        )!!,
                        product_name!!,
                        product_details!!,
                        product_code!!,
                        image_address!!,
                        id_unit!!,
                        sell_price!!.toDouble(),
                        supplier_price!!.toDouble(),
                        SharedPreferenceUtil.getShared(
                            activity!!,
                            SharedPreferenceUtil.TYPE_SHOP_ID
                        )!!.toInt(),
                        stock!!.toInt(),
                        discount!!.toDouble(),
                        currentDate!!,
                        id_category!!,
                        status!!
                    )

//                    viewModel.postSupplier(
//                        SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,name,mobile,email,address,details,image_address!!,status!!,
//                        SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),currentDate)
                }
            } else {
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                if (switch_status?.isChecked!!) {
                    status = 1
                } else {
                    status = 0
                }

                var product_name: String = ""
                var product_details: String = ""
                var product_code: String = ""
                var sell_price: String = ""
                var supplier_price: String = ""
                var stock: String = ""
                var discount: String = ""
                product_name = et_product_name?.text.toString()
                product_details = et_details?.text.toString()
                product_code = et_product_code?.text.toString()
                sell_price = et_sell_price?.text.toString()
                stock = et_stock?.text.toString()
                supplier_price = et_supplier_price?.text.toString()
                discount = et_discount?.text.toString()

                if (product_name.isNullOrEmpty() && product_details.isNullOrEmpty() && product_code.isNullOrEmpty() && sell_price.isNullOrEmpty() && supplier_price.isNullOrEmpty() && stock.isNullOrEmpty() && discount.isNullOrEmpty() ) {
                    root_layout?.snackbar("All Field is Empty")
                } else if (product_name.isNullOrEmpty()) {
                    root_layout?.snackbar("Product Name is Empty")
                } else if (product_details.isNullOrEmpty()) {
                    root_layout?.snackbar("Product Details is Empty")
                } else if (product_code.isNullOrEmpty()) {
                    root_layout?.snackbar("Product Code is Empty")
                } else if (sell_price.isNullOrEmpty()) {
                    root_layout?.snackbar("Sell Price is Empty")
                } else if (supplier_price.isNullOrEmpty()) {
                    root_layout?.snackbar("Supplier Price is Empty")
                } else if (stock.isNullOrEmpty()) {
                    root_layout?.snackbar("Stock is Empty")
                }  else if (discount.isNullOrEmpty()) {
                    root_layout?.snackbar("Discount is Empty")
                }
                else if (image_address.isNullOrEmpty()) {
                    root_layout?.snackbar("Image is Empty")
                }
                else if (id_category==0) {
                    root_layout?.snackbar("Product Category is Empty.Please Add Product Category")
                }
                else {
                    Log.e("Evan", "Evan")
                    viewModel.postUpdateProduct(
                        SharedPreferenceUtil.getShared(
                            activity!!,
                            SharedPreferenceUtil.TYPE_AUTH_TOKEN
                        )!!,
                        id!!,
                        product_name!!,
                        product_details!!,
                        product_code!!,
                        image_address!!,
                        id_unit!!,
                        sell_price!!.toDouble(),
                        supplier_price!!.toDouble(),

                        SharedPreferenceUtil.getShared(
                            activity!!,
                            SharedPreferenceUtil.TYPE_SHOP_ID
                        )!!.toInt(),
                        stock!!.toInt(),
                        discount!!.toDouble(),
                        currentDate!!,
                        id_category!!,
                        status!!
                    )
//                    viewModel.postUpdateSupplier(
//                        SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,id!!,name,mobile,email,address,details,image_address!!,status!!,
//                SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),currentDate)
                }
            }
        }
        return root
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
                        id_unit = units.get(position).Id
                        for (i in unit!!.indices) {
                            Log.e("unit_id","unit_id"+ unit!!.get(i).Id!!)
                            Log.e("unit_id_","unit_id_"+ product?.UnitId)
                            if (unit!!.get(i).Id!!.equals(product?.UnitId)) {
                                spinner_unit?.setSelection(i)
                                Log.e("trues","trues")
                            }
                        }
                        Log.e("shop", "shop" + units.get(position).Id)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Another interface callback
                        Log.e("sdf", "S")
                    }

                }
        }
    }

    override fun category(categories: MutableList<CategoryType>?) {
        category = categories
        categories?.let {
            var mListUnitList: ArrayList<String>? = ArrayList<String>()
            for (unit in categories) {
                mListUnitList?.add(unit?.Name!!)
            }
            categoryDataAdapter = ArrayAdapter<String>(
                activity as Activity,
                android.R.layout.simple_spinner_item,
                mListUnitList!!
            )
            categoryDataAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_category?.adapter = categoryDataAdapter
            spinner_category?.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        id_category = categories!!.get(position).Id!!.toInt()
                        for (i in category!!.indices) {
                            if (category!!.get(i).Id!!.equals(product?.ProductCategoryId.toString())) {
                                spinner_category?.setSelection(i)
                            }
                        }
                        Log.e("shop", "shop" + categories.get(position).Id)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Another interface callback
                        Log.e("sdf", "S")
                    }

                }
        }
    }

    override fun supplier(suppliers: MutableList<Supplier>?) {
        supplier = suppliers
        supplier?.let {
            var mListUnitList: ArrayList<String>? = ArrayList<String>()
            for (unit in suppliers!!) {
                mListUnitList?.add(unit?.Name!!)
            }
            supplierDataAdapter = ArrayAdapter<String>(
                activity as Activity,
                android.R.layout.simple_spinner_item,
                mListUnitList!!
            )
            supplierDataAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_supplier?.adapter = supplierDataAdapter
            spinner_supplier?.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        id_supplier = suppliers.get(position).Id
                        Log.e("supplier", "supplier" + suppliers.get(position).Id)
                        for (i in supplier!!.indices) {
                            if (supplier!!.get(i).Id!!.equals(product?.SupplierId)) {
                                spinner_supplier?.setSelection(i)
                            }
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Another interface callback
                        Log.e("sdf", "S")
                    }

                }
        }
    }

    override fun show(value: String) {
        Toast.makeText(activity, value, Toast.LENGTH_LONG).show()
        if (activity is HomeActivity) {
            (activity as HomeActivity).backPress()
        }
    }

    override fun failure(value: String) {
        Toast.makeText(activity, value, Toast.LENGTH_LONG).show()
    }

    override fun started() {
        progress_bar?.show()
    }

    override fun end() {
        progress_bar?.hide()
    }
    fun showImage(temp:String?){
//        image_address="http://hathbazzar.com/"+temp
//        Log.e("for","Image"+temp)
//        Glide.with(this)
//            .load("http://hathbazzar.com/"+temp)
//            .into(img_background_mypage!!)
//        img_user_add?.visibility=View.INVISIBLE

        image_address="http://192.168.0.105/"+temp
        Log.e("for","Image"+temp)
        Glide.with(this)
            .load("http://192.168.0.105/"+temp)
            .into(img_background_mypage!!)
        img_user_add?.visibility=View.INVISIBLE
    }

}
