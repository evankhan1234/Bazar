package com.evan.bazar.ui.home.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.evan.bazar.R
import com.evan.bazar.ui.fragments.StepOneFragment
import com.evan.bazar.ui.fragments.StepTwoFragment
import com.evan.bazar.ui.home.HomeActivity
import com.evan.bazar.ui.home.category.CategoryFragment


class StoreFragment : Fragment() {

    var linear_category:LinearLayout?=null
    var linear_supplier:LinearLayout?=null
    var linear_purchase:LinearLayout?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_store, container, false)
        linear_category=root.findViewById(R.id.linear_category)
        linear_supplier=root.findViewById(R.id.linear_supplier)
        linear_purchase=root.findViewById(R.id.linear_purchase)
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
