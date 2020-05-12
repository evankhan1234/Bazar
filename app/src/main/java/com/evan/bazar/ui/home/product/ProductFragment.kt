package com.evan.bazar.ui.home.product

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import com.evan.bazar.ui.home.supplier.SupplierSearchAdapter
import com.evan.bazar.util.NetworkState
import com.evan.bazar.util.SharedPreferenceUtil
import com.evan.bazar.util.hide
import com.evan.bazar.util.show
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class ProductFragment : Fragment(),KodeinAware,IProductUpdateListener ,IProductListener{
    override val kodein by kodein()

    private val factory : ProductModelFactory by instance()
    var rcv_search:RecyclerView?=null
    var producteAdapter: ProductAdapter?=null
    var adapter_search: ProductSearchAdapter?=null
    var edit_content: EditText?=null
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
        rcv_search=root?.findViewById(R.id.rcv_search)
        progress_bar=root?.findViewById(R.id.progress_bar)
        rcv_product=root?.findViewById(R.id.rcv_product)
        btn_product_new=root?.findViewById(R.id.btn_product_new)
        edit_content=root?.findViewById(R.id.edit_content)
        edit_content?.addTextChangedListener(keyword)
        viewModel = ViewModelProviders.of(this, factory).get(ProductViewModel::class.java)
        viewModel.listener=this
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
        rcv_search?.visibility=View.GONE
        rcv_product?.visibility=View.VISIBLE
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

    override fun show(data: MutableList<Product>) {
        rcv_search?.visibility=View.VISIBLE
        rcv_product?.visibility=View.GONE
        viewModel.replaceSubscription(this)
        adapter_search = ProductSearchAdapter(context!!,data!!,this)
        rcv_search?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = adapter_search
        }
    }

    override fun started() {
        progress_bar?.show()
    }

    override fun end() {
        progress_bar?.hide()
    }

    var keyword: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            try {
                if (s.toString().equals("")){
                    startListening()
                }
                else{
                    var keyword:String?=""
                    keyword=s.toString()+"%"
                    viewModel.getProduct(token!!,keyword)
                }

            } catch (e: Exception) {

            }


        }

    }
    override fun exit(){
        rcv_search?.visibility=View.GONE
        rcv_product?.visibility=View.GONE
        viewModel.replaceSubscription(this)
    }
}
