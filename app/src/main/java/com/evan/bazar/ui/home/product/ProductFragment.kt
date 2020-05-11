package com.evan.bazar.ui.home.product

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.evan.bazar.R
import com.evan.bazar.data.db.entities.Product
import com.evan.bazar.data.db.entities.Purchase
import com.evan.bazar.ui.home.HomeActivity
import com.evan.bazar.ui.home.purchase.PurchaseAdapter
import com.evan.bazar.ui.home.purchase.PurchaseModelFactory
import com.evan.bazar.ui.home.purchase.PurchaseViewModel
import com.evan.bazar.ui.home.supplier.CreateSupplierFragment
import com.evan.bazar.util.NetworkState
import com.evan.bazar.util.SharedPreferenceUtil
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class ProductFragment : Fragment(),KodeinAware,IProductUpdateListener {
    override val kodein by kodein()

    private val factory : ProductModelFactory by instance()

    var producteAdapter: ProductAdapter?=null

    var rcv_product: RecyclerView?=null
    var progress_bar: ProgressBar?=null
    var btn_product_new: ImageView?=null
    var token:String?=""
    private lateinit var viewModel: ProductViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_product, container, false)
        progress_bar=root?.findViewById(R.id.progress_bar)
        rcv_product=root?.findViewById(R.id.rcv_product)
        btn_product_new=root?.findViewById(R.id.btn_product_new)
        viewModel = ViewModelProviders.of(this, factory).get(ProductViewModel::class.java)

        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)


        btn_product_new?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToCreateProductFragment()
            }
        }
        return root
    }

    override fun onUpdate(product: Product) {
        if (activity is HomeActivity) {
            (activity as HomeActivity).goToUpdateProductFragment(product)
        }
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
        producteAdapter = ProductAdapter(context!!,this)
        rcv_product?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rcv_product?.adapter = producteAdapter
        startListening()
    }

    private fun startListening() {

        viewModel.listOfAlerts?.observe(this, Observer {
            producteAdapter?.submitList(it)
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

    fun removeChild() {
        val f =
            childFragmentManager.findFragmentByTag(CreateSupplierFragment::class.java.simpleName)
        val f1 = childFragmentManager.findFragmentByTag(CreateSupplierFragment::class.java.simpleName)
        if (f != null) {
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right)
            transaction.remove(f)
            transaction.commit()
            childFragmentManager.popBackStack()
        }
    }



}
