package com.evan.bazar.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.evan.bazar.R
import com.evan.bazar.ui.home.category.CategoryFragment
import com.evan.bazar.ui.home.category.CreateCategoryFragment
import com.evan.bazar.ui.home.dashboard.DashboardFragment
import com.evan.bazar.ui.home.order.OrderFragment
import com.evan.bazar.ui.home.settings.SettingsFragment
import com.evan.bazar.ui.home.store.StoreFragment
import com.evan.bazar.util.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_layout.*
import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import java.util.*

class HomeActivity : AppCompatActivity() {
    var mFragManager: FragmentManager? = null
    var fragTransaction: FragmentTransaction? = null
    var mCurrentFrag: Fragment? = null
    var CURRENT_PAGE: Int? = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUpHeader(FRAG_TOP)
        afterClickTabItem(FRAG_TOP, null)
        setUpFooter(FRAG_TOP)
        img_header_back?.setOnClickListener {
            onBackPressed()
        }
    }
    fun btn_home_clicked(view: View) {
        setUpHeader(FRAG_TOP)
        afterClickTabItem(FRAG_TOP, null)
        setUpFooter(FRAG_TOP)

    }

    fun btn_store_clicked(view: View) {
        setUpHeader(FRAG_STORE)
        afterClickTabItem(FRAG_STORE, null)
        setUpFooter(FRAG_STORE)
        //ccheckPP()


    }

    fun btn_orders_clicked(view: View) {
        setUpHeader(FRAG_ORDER)
        afterClickTabItem(FRAG_ORDER, null)
        setUpFooter(FRAG_ORDER)

    }

    fun btn_settings_clicked(view: View) {
        setUpHeader(FRAG_SETTINGS)
        afterClickTabItem(FRAG_SETTINGS, null)
        setUpFooter(FRAG_SETTINGS)
    }

    @Suppress("UNUSED_PARAMETER")
    fun afterClickTabItem(fragId: Int, obj: Any?) {
        addFragment(fragId, false, obj)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val f = getVisibleFragment()
        val f1 = getLastParentFragment()
        if (f != null)
        {
            if (f is StoreFragment) {
                val storeFragment: StoreFragment =
                    mFragManager?.findFragmentByTag(FRAG_STORE.toString()) as StoreFragment
                storeFragment.removeChild()
                setUpHeader(FRAG_STORE)
            }
            if (f is CategoryFragment) {
                val storeFragment: CategoryFragment =
                    mFragManager?.findFragmentByTag(FRAG_CATEGORY.toString()) as CategoryFragment
                storeFragment.removeChild()
                setUpHeader(FRAG_CATEGORY)
            }

        }

    }
    fun backPress() {
        hideKeyboard(this)

    }

    fun goToCategoryFragment() {
        setUpHeader(FRAG_CATEGORY)
        addFragment(FRAG_CATEGORY, true, null)

    }
    fun goToCreateCategoryFragment() {
        setUpHeader(FRAG_CREATE_CATEGORY)
        addFragment(FRAG_CREATE_CATEGORY, true, null)

    }
    fun addFragment(fragId: Int, isHasAnimation: Boolean, obj: Any?) {
        // init fragment manager
        mFragManager = supportFragmentManager
        // create transaction
        fragTransaction = mFragManager?.beginTransaction()
        //check if there is any backstack if yes then remove it
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            // mFragManager?.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }
        var newFrag: Fragment? = null
        // identify which fragment will be called
        when (fragId) {
            FRAG_TOP -> {
                newFrag = DashboardFragment()
            }
            FRAG_STORE -> {
                newFrag = StoreFragment()
            }
            FRAG_ORDER -> {
                newFrag = OrderFragment()
            }
            FRAG_SETTINGS -> {
                newFrag = SettingsFragment()
            }
            FRAG_CATEGORY -> {
                newFrag = CategoryFragment()
            }
            FRAG_CREATE_CATEGORY-> {
                newFrag = CreateCategoryFragment()
            }

        }
        mCurrentFrag = newFrag
        // init argument
        if (obj != null) {
            val args = Bundle()
        }
        // set animation
        if (isHasAnimation) {
            fragTransaction!!.setCustomAnimations(
                R.anim.view_transition_in_left,
                R.anim.view_transition_out_left,
                R.anim.view_transition_in_right,
                R.anim.view_transition_out_right
            )
        }
        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

    }
    fun getLastParentFragment(): Fragment? {
        val fragmentManager = mFragManager
        val fragments = fragmentManager!!.fragments
        fragments.reverse()
        for (fragment in fragments) {
            if (fragment != null && fragment.isVisible) {
                return fragment
            }
        }
        return null
    }
    fun setUpHeader(type: Int) {
        when (type) {
            FRAG_TOP -> {
                ll_back_header?.visibility = View.GONE
                rlt_header?.visibility = View.VISIBLE
                tv_title.text = resources.getString(R.string.home)
            }
            FRAG_STORE -> {
                ll_back_header?.visibility = View.GONE
                rlt_header?.visibility = View.VISIBLE
                tv_title.text = resources.getString(R.string.store)

            }
            FRAG_ORDER -> {
                ll_back_header?.visibility = View.GONE
                rlt_header?.visibility = View.VISIBLE

                tv_title.text = resources.getString(R.string.order)

            }
            FRAG_SETTINGS -> {
                ll_back_header?.visibility = View.GONE
                rlt_header?.visibility = View.VISIBLE
                tv_title.text = resources.getString(R.string.settings)
                btn_footer_settings.setSelected(true)

            }
            FRAG_CATEGORY -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_title.text = resources.getString(R.string.settings)
                btn_footer_store.setSelected(true)

            }

            else -> {

            }
        }
    }
    fun getVisibleFragment(): Fragment? {
        val fragmentManager = mFragManager
        val fragments = fragmentManager!!.fragments
        fragments.reverse()
        for (fragment in fragments!!) {
            if (fragment != null && fragment.isVisible) {
                return fragment
            }
        }
        return null
    }
    fun setUpFooter(type: Int) {
        setUnselectAllmenu()
        CURRENT_PAGE = type

        when (type) {
            FRAG_TOP -> {
                shadow_line?.visibility = View.VISIBLE
                bottom_navigation?.visibility = View.VISIBLE
                
                btn_footer_home.setSelected(true)
                tv_home_menu.setSelected(true)
            }
            FRAG_STORE -> {
                shadow_line?.visibility = View.VISIBLE
                bottom_navigation?.visibility = View.VISIBLE
                
                btn_footer_store.setSelected(true)
                tv_store_menu.setSelected(true)
            }
            FRAG_ORDER -> {
                shadow_line?.visibility = View.VISIBLE
                bottom_navigation?.visibility = View.VISIBLE
                
                btn_footer_orders.setSelected(true)
                tv_orders_menu.setSelected(true)
            }
            FRAG_SETTINGS-> {
                shadow_line?.visibility = View.VISIBLE
                bottom_navigation?.visibility = View.VISIBLE
                
                btn_footer_settings.setSelected(true)
                tv_settings_menu.setSelected(true)
            }
            FRAG_NOTICE -> {
                shadow_line?.visibility = View.VISIBLE
                bottom_navigation?.visibility = View.VISIBLE
                

            }


            else -> {

            }

        }
    }

    private fun setUnselectAllmenu() {
        btn_footer_home.setSelected(false)
        tv_home_menu.setSelected(false)

        btn_footer_store.setSelected(false)
        tv_store_menu.setSelected(false)

        btn_footer_orders.setSelected(false)
        tv_orders_menu.setSelected(false)

        btn_footer_settings.setSelected(false)
        tv_settings_menu.setSelected(false)
    }

    fun setAnimation(isAnimation: Boolean) {
        if (isAnimation) {
            fragTransaction!!.setCustomAnimations(
                R.anim.view_transition_in_left,
                R.anim.view_transition_out_right, R.anim.view_transition_in_left,
                R.anim.view_transition_out_right
            )
        }
    }
}
