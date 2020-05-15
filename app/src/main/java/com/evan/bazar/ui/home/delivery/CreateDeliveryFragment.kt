package com.evan.bazar.ui.home.delivery

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.evan.bazar.R
import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.data.db.entities.CustomerOrder
import com.evan.bazar.data.db.entities.Orders
import com.evan.bazar.ui.home.HomeActivity
import com.evan.bazar.ui.home.HomeViewModel
import com.evan.bazar.ui.home.HomeViewModelFactory
import com.evan.bazar.ui.home.order.OrdersAdapter
import com.evan.bazar.util.SharedPreferenceUtil
import com.evan.bazar.util.hide
import com.evan.bazar.util.show
import com.google.gson.Gson
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class CreateDeliveryFragment : Fragment(),KodeinAware,ICustomerOrderListListener {
    override val kodein by kodein()
    var progress_bar: ProgressBar?=null
    var rcv_orders: RecyclerView?=null
    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var order:Orders?=null
    var token:String?=""
    var ordersAdapter:CustomerOrderAdapter?=null
    var btn_ok:Button?=null
    var tv_sub_total:TextView?=null
    var tv_invoice_number:TextView?=null
    var tv_total:TextView?=null
    var tv_grand_total:TextView?=null
    var et_discount:EditText?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_create_delivery, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.customerOrderListener=this
        progress_bar=root?.findViewById(R.id.progress_bar)
        rcv_orders=root?.findViewById(R.id.rcv_orders)
        btn_ok=root?.findViewById(R.id.btn_ok)
        tv_sub_total=root?.findViewById(R.id.tv_sub_total)
        tv_invoice_number=root?.findViewById(R.id.tv_invoice_number)
        tv_grand_total=root?.findViewById(R.id.tv_grand_total)
        tv_total=root?.findViewById(R.id.tv_total)
        et_discount=root?.findViewById(R.id.et_discount)
        et_discount?.addTextChangedListener(discount)

        val args: Bundle? = arguments
        if (args != null) {
            if (args?.containsKey(Orders::class.java.getSimpleName()) != null) {
                order = args?.getParcelable(Orders::class.java.getSimpleName())

                Log.e("data","data"+ Gson().toJson(order))
                tv_invoice_number?.text="Invoice Number : "+getStartDate(order?.Created)+"#"+order?.Id
            }
        }
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        viewModel.getCustomerOrders(token!!,order?.Id!!)

        btn_ok?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).backPress()
            }
        }
        return root
    }
    var sub_total:Double?=0.0
    var total_value:Double?=0.0
    override fun order(data: MutableList<CustomerOrder>?) {
        var price:Double?=0.0
        for(i in data?.indices!!){
            price=price!!+data?.get(i).Price!!
        }

        val number2digits:Double = String.format("%.2f", price).toDouble()
        sub_total=number2digits
        Log.e("Price","Price"+number2digits)
        tv_sub_total?.text=number2digits?.toString()+" Tk"
        tv_total?.text=number2digits?.toString()+" Tk"
        tv_grand_total?.text=number2digits?.toString()+" Tk"
        ordersAdapter = CustomerOrderAdapter(context!!,data!!,this)
        rcv_orders?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
            adapter = ordersAdapter
        }
    }
    fun getStartDate(startDate: String?): String? {
        val inputFormat =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val outputFormat =
            DateTimeFormatter.ofPattern("yyyyMMdd")
        return LocalDate.parse(startDate, inputFormat).format(outputFormat)
    }
    override fun onStarted() {
        progress_bar?.show()
    }

    override fun onEnd() {
        progress_bar?.hide()
    }

    var discount: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            try {

                var discount:Double?=0.0
                var total:Double?=0.0
                discount=s.toString().toDouble()
                total=sub_total!!-discount!!
                val number2digits:Double = String.format("%.2f", total).toDouble()
                tv_total?.setText(number2digits.toString())
                tv_grand_total?.setText(number2digits.toString())

            } catch (e: Exception) {
                val number2digits:Double = String.format("%.2f", sub_total).toDouble()
                total_value=number2digits
                tv_total?.setText(number2digits.toString())
                tv_grand_total?.setText(number2digits.toString())
            }


        }

    }
}
