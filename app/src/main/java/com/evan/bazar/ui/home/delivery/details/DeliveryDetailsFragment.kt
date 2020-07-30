package com.evan.bazar.ui.home.delivery.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.CustomerOrderDetails
import com.evan.bazar.data.db.entities.CustomerOrderList
import com.evan.bazar.data.db.entities.Delivery
import com.evan.bazar.ui.home.HomeViewModel
import com.evan.bazar.ui.home.HomeViewModelFactory
import com.evan.bazar.util.SharedPreferenceUtil
import com.google.gson.Gson
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class DeliveryDetailsFragment : Fragment(), KodeinAware,ICustomerOrderListener,ICustomerOrderListForListener,IReturnListener {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var ordersAdapter: OrderAdapter? = null
    var delivery: Delivery? = null
    var token: String? = ""
    var rcv_orders:RecyclerView?=null
    var tv_invoice: TextView?=null
    var tv_discount: TextView?=null
    var tv_total: TextView?=null
    var tv_delivery_charge: TextView?=null
    var tv_total_amount: TextView?=null
    var tv_sub_total: TextView?=null
    var btn_cancel: Button?=null
    var customerOrders: CustomerOrderDetails?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_delivery_details, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.customerOrderInformationListener=this
        viewModel.customerOrderForListener=this

        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        val args: Bundle? = arguments
        if (args != null) {
            if (args?.containsKey(Delivery::class.java.getSimpleName()) != null) {
                delivery = args?.getParcelable(Delivery::class.java.getSimpleName())

                Log.e("data", "data" + Gson().toJson(delivery!!))

            }
        }

        tv_total_amount=root?.findViewById(R.id.tv_total_amount)
        tv_delivery_charge=root?.findViewById(R.id.tv_delivery_charge)
        rcv_orders=root?.findViewById(R.id.rcv_orders)
        tv_discount=root?.findViewById(R.id.tv_discount)
        tv_total=root?.findViewById(R.id.tv_total)
        tv_invoice=root?.findViewById(R.id.tv_invoice)
        tv_sub_total=root?.findViewById(R.id.tv_sub_total)
        viewModel.getCustomerOrdersList(token!!, delivery?.OrderId!!)
        viewModel.getCustomerOrderInformation(token!!,delivery?.OrderId!!.toInt())

        return root;
    }

    override fun onShow(customerOrder: CustomerOrderDetails?) {
        tv_invoice?.text=customerOrder?.InvoiceNumber
        tv_discount?.text=customerOrder?.Discount+" ট"
        tv_total?.text=customerOrder?.Total+" ট"
        tv_delivery_charge?.text=customerOrder?.DeliveryCharge+" ট"

        customerOrders=customerOrder

        var sub:Double?
        var minus:Double?
        val sub_total=customerOrder?.Total?.toDouble()!!+customerOrder?.DeliveryCharge?.toDouble()!!
        minus=customerOrder?.Total?.toDouble()!!+customerOrder?.Discount?.toDouble()!!
        sub=sub_total.toDouble()
        val number2digits: Double = String.format("%.2f", sub).toDouble()
        val number2digitsForSubTotal: Double = String.format("%.2f", minus).toDouble()
        tv_total_amount?.text=number2digits.toString()+" ট"
        tv_sub_total?.text=number2digitsForSubTotal.toString()+" ট"
    }

    override fun onEmpty() {

    }

    override fun onShow(customerOrderList: CustomerOrderList, reason: String) {
        viewModel?.updateReturnOrderStatus(token!!,customerOrderList?.Id!!,2,reason)

        ordersAdapter?.notifyDataSetChanged()
    }

    override fun order(data: MutableList<CustomerOrderList>?) {
        ordersAdapter = OrderAdapter(context!!, data!!, this)
        rcv_orders?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = ordersAdapter
        }
    }

    override fun onStarted() {

    }

    override fun onEnd() {

    }


}