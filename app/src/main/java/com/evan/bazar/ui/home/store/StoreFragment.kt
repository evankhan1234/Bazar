package com.evan.bazar.ui.home.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.evan.bazar.R
import com.evan.bazar.ui.fragments.StepOneFragment
import com.evan.bazar.ui.fragments.StepTwoFragment
import com.evan.bazar.ui.home.HomeActivity
import com.evan.bazar.ui.home.category.CategoryFragment
import com.evan.bazar.util.SharedPreferenceUtil
import java.text.SimpleDateFormat
import java.util.*


class StoreFragment : Fragment() {

    var linear_category:LinearLayout?=null
    var linear_supplier:LinearLayout?=null
    var linear_purchase:LinearLayout?=null
    var linear_product:LinearLayout?=null
    var tv_shop_name:TextView?=null
    var tv_date:TextView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_store, container, false)
        linear_category=root.findViewById(R.id.linear_category)
        linear_supplier=root.findViewById(R.id.linear_supplier)
        linear_purchase=root.findViewById(R.id.linear_purchase)
        linear_product=root.findViewById(R.id.linear_product)
        tv_shop_name=root.findViewById(R.id.tv_shop_name)
        tv_date=root.findViewById(R.id.tv_date)
        tv_shop_name?.text= SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_NAME)
        val sdf = SimpleDateFormat("dd,MMMM yyyy")
        val currentDate = sdf.format(Date())
        tv_date?.text=currentDate
        linear_category?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToCategoryFragment()
            }
        }
        linear_supplier?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToSupplierFragment()
            }
        }
        linear_purchase?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToPurchaseFragment()
            }
        }
        linear_product?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToProductFragment()
            }
        }
        return root
    }
    fun removeChild() {
        val f =
            childFragmentManager.findFragmentByTag(CategoryFragment::class.java.simpleName)
        val f1 = childFragmentManager.findFragmentByTag(CategoryFragment::class.java.simpleName)
        if (f != null) {
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right)
            transaction.remove(f)
            transaction.commit()
            childFragmentManager.popBackStack()
        }
    }
}
