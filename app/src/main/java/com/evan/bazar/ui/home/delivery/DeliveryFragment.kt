package com.evan.bazar.ui.home.delivery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.evan.bazar.R
import com.evan.bazar.data.db.entities.Delivery
import com.evan.bazar.ui.home.supplier.SupplierAdapter
import com.evan.bazar.ui.home.supplier.SupplierModelFactory
import com.evan.bazar.ui.home.supplier.SupplierViewModel
import com.evan.bazar.util.NetworkState
import com.evan.bazar.util.SharedPreferenceUtil
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class DeliveryFragment : Fragment(),KodeinAware ,IDeliveryUpdateListener{
    override val kodein by kodein()

    private val factory : DeliveryModelFactory by instance()
    private lateinit var viewModel: DeliveryViewModel

    var rcv_search: RecyclerView?=null
    var delivery_Adapter: DeliveryAdapter?=null
    var edit_content: EditText?=null
    var rcv_deliveries: RecyclerView?=null
    var progress_bar: ProgressBar?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_delivery, container, false)
        rcv_deliveries=root?.findViewById(R.id.rcv_deliveries)
        edit_content=root?.findViewById(R.id.edit_content)
        progress_bar=root?.findViewById(R.id.progress_bar)
        viewModel = ViewModelProviders.of(this, factory).get(DeliveryViewModel::class.java)


        return root
    }
    fun replace(){
        viewModel.replaceSubscription(this)
        startListening()
    }
    override fun onResume() {
        super.onResume()
        // viewModel.getCategoryType(token!!)
        Log.e("stop","stop")
        initAdapter()
        initState()
    }

    private fun initAdapter() {
        delivery_Adapter = DeliveryAdapter(context!!,this)
        rcv_deliveries?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rcv_deliveries?.adapter = delivery_Adapter
        startListening()
    }

    private fun startListening() {

        viewModel.listOfAlerts?.observe(this, Observer {
            delivery_Adapter?.submitList(it)
        })

    }


    private fun initState() {
        viewModel.getNetworkState().observe(this, Observer { state ->
            when (state.status) {
                NetworkState.Status.LOADIND -> {
                    progress_bar?.visibility=View.VISIBLE
                }
                NetworkState.Status.SUCCESS -> {
                    progress_bar?.visibility=View.GONE
                }
                NetworkState.Status.FAILED -> {
                    progress_bar?.visibility=View.GONE
                }
            }
        })
    }

    override fun onUpdate(delivery: Delivery) {

    }


}
