package com.evan.bazar.ui.home.delivery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.evan.bazar.R
import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.data.db.entities.CustomerOrder
import com.evan.bazar.data.db.entities.Orders
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


class CreateDeliveryFragment : Fragment(),KodeinAware,ICustomerOrderListListener {
    override val kodein by kodein()
    var progress_bar: ProgressBar?=null
    var rcv_orders: RecyclerView?=null
    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var order:Orders?=null
    var token:String?=""
    var ordersAdapter:CustomerOrderAdapter?=null
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
        val args: Bundle? = arguments
        if (args != null) {
            if (args?.containsKey(Orders::class.java.getSimpleName()) != null) {
                order = args?.getParcelable(Orders::class.java.getSimpleName())

                Log.e("data","data"+ Gson().toJson(order))
            }
        }
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        viewModel.getCustomerOrders(token!!)
        return root
    }

    override fun order(data: MutableList<CustomerOrder>?) {
        ordersAdapter = CustomerOrderAdapter(context!!,data!!,this)
        rcv_orders?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
            adapter = ordersAdapter
        }
    }

    override fun onStarted() {
        progress_bar?.show()
    }

    override fun onEnd() {
        progress_bar?.hide()
    }


}
