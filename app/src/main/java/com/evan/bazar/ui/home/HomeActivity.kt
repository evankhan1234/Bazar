package com.evan.bazar.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.evan.bazar.BuildConfig
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.*
import com.evan.bazar.interfaces.DialogActionListener
import com.evan.bazar.ui.auth.ImageUpdateActivity
import com.evan.bazar.ui.fragments.StepOneFragment
import com.evan.bazar.ui.home.category.CategoryFragment
import com.evan.bazar.ui.home.category.CreateCategoryFragment
import com.evan.bazar.ui.home.dashboard.DashboardFragment
import com.evan.bazar.ui.home.delivery.CreateDeliveryFragment
import com.evan.bazar.ui.home.newsfeed.NewsfeedFragment
import com.evan.bazar.ui.home.newsfeed.ownpost.PostBottomsheetFragment
import com.evan.bazar.ui.home.newsfeed.publicpost.comments.CommentsFragment
import com.evan.bazar.ui.home.order.OrderFragment
import com.evan.bazar.ui.home.orderdelivery.OrderDeliveryFragment
import com.evan.bazar.ui.home.product.CreateProductFragment
import com.evan.bazar.ui.home.product.ProductFragment
import com.evan.bazar.ui.home.purchase.CreatePurchaseFragment
import com.evan.bazar.ui.home.purchase.PurchaseFragment
import com.evan.bazar.ui.home.settings.SettingsFragment
import com.evan.bazar.ui.home.store.StoreFragment
import com.evan.bazar.ui.home.supplier.CreateSupplierFragment
import com.evan.bazar.ui.home.supplier.SupplierFragment
import com.evan.bazar.util.*
import com.evan.dokan.ui.home.notice.NoticeFragment
import com.evan.dokan.ui.home.notice.NoticeViewFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_layout.*
import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import java.io.File
import java.io.IOException
import java.util.*

class HomeActivity : AppCompatActivity() {
    var mFragManager: FragmentManager? = null
    var fragTransaction: FragmentTransaction? = null
    var mCurrentFrag: Fragment? = null
    var CURRENT_PAGE: Int? = 1
    private var fresh: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fresh = SharedPreferenceUtil.getShared(this, SharedPreferenceUtil.TYPE_FRESH)
        Log.e("TAG", fresh!! + "")
        if (fresh != null && !fresh?.trim().equals("") && !fresh.isNullOrEmpty()) {


        } else {
            progress_circular_home?.show()
            Handler().postDelayed(Runnable {
                SharedPreferenceUtil.saveShared(
                    this,
                    SharedPreferenceUtil.TYPE_FRESH,
                    "Fresh"
                )

                showDialogSetUp(this,
                    "Set Up Completed",
                    "OK",
                    "OK",
                    object :
                        com.evan.bazar.util.DialogActionListener {
                        override fun onPositiveClick() {

                        }

                        override fun onNegativeClick() {

                        }
                    })
                progress_circular_home?.hide()
            },5000)
        }
        setUpHeader(FRAG_TOP)
        afterClickTabItem(FRAG_TOP, null)
        setUpFooter(FRAG_TOP)
        img_header_back?.setOnClickListener {
            onBackPressed()
        }
        btn_notice?.setOnClickListener {
            setUpHeader(FRAG_NOTICE)
            afterClickTabItem(FRAG_NOTICE, null)
            setUpFooter(FRAG_NOTICE)
        }
        btn_foods?.setOnClickListener {
           goToNewsfeedFragment()
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
        if(fragId==5 || fragId==19){
            addFragment(fragId, true, obj)
        }
        else{
            addFragment(fragId, false, obj)
        }

    }
    fun onBottomBackPress(){
        val f = getVisibleFragment()
        if(f is NewsfeedFragment){
            f.reload()
        }
    }
    fun goToNewsfeedFragment(){
        setUpHeader(FRAG_NEWSFEED)
        afterClickTabItem(FRAG_NEWSFEED, null)
    }
    fun onBottomCommentsBackPress(){
        val f = getVisibleFragmentsForComments()
        if(f is CommentsFragment){
            f.reload()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()

        val f = getVisibleFragment()
        if (f != null)
        {
            if (f is StoreFragment) {
                val storeFragment: StoreFragment =
                    mFragManager?.findFragmentByTag(FRAG_STORE.toString()) as StoreFragment
                storeFragment.removeChild()
                setUpHeader(FRAG_STORE)
            }
            if (f is DashboardFragment) {
                val storeFragment: DashboardFragment =
                    mFragManager?.findFragmentByTag(FRAG_TOP.toString()) as DashboardFragment
              //  storeFragment.removeChild()
                setUpHeader(FRAG_TOP)
            }
            if (f is OrderDeliveryFragment) {
                val orderDeliveryFragment: OrderDeliveryFragment =
                    mFragManager?.findFragmentByTag(FRAG_ORDER.toString()) as OrderDeliveryFragment
                orderDeliveryFragment.removeChild()
                setUpHeader(FRAG_ORDER)
            }
            if (f is CategoryFragment) {
                val storeFragment: CategoryFragment =
                    mFragManager?.findFragmentByTag(FRAG_CATEGORY.toString()) as CategoryFragment
                storeFragment.replace()
                storeFragment.removeChild()
                setUpHeader(FRAG_CATEGORY)
            }
            if (f is SupplierFragment) {
                val supplierFragment: SupplierFragment =
                    mFragManager?.findFragmentByTag(FRAG_SUPPLIER.toString()) as SupplierFragment
                supplierFragment.replace()
                supplierFragment.removeChild()
                setUpHeader(FRAG_SUPPLIER)
            }
            if (f is PurchaseFragment) {
                val purchaseFragment: PurchaseFragment =
                    mFragManager?.findFragmentByTag(FRAG_PURCHASE.toString()) as PurchaseFragment
                purchaseFragment.replace()
                purchaseFragment.removeChild()
                setUpHeader(FRAG_PURCHASE)
            }
            if (f is ProductFragment) {
                val productFragment: ProductFragment =
                    mFragManager?.findFragmentByTag(FRAG_PRODUCT.toString()) as ProductFragment
                productFragment.replace()
                productFragment.removeChild()
                setUpHeader(FRAG_PRODUCT)
            }
            else if (f is NoticeFragment) {
                val noticeFragment: NoticeFragment =
                    mFragManager?.findFragmentByTag(FRAG_NOTICE.toString()) as NoticeFragment
                noticeFragment.removeChild()
                setUpHeader(FRAG_NOTICE )
            }
        }

    }
    fun backPress() {
        hideKeyboard(this)
        onBackPressed()

    }

    fun goToCategoryFragment() {
        setUpHeader(FRAG_CATEGORY)
        addFragment(FRAG_CATEGORY, true, null)

    }
    fun goToSupplierFragment() {
        setUpHeader(FRAG_SUPPLIER)
        addFragment(FRAG_SUPPLIER, true, null)

    }
    fun goToPurchaseFragment() {
        setUpHeader(FRAG_PURCHASE)
        addFragment(FRAG_PURCHASE, true, null)

    }
    fun goToProductFragment() {
        setUpHeader(FRAG_PRODUCT)
        addFragment(FRAG_PRODUCT, true, null)

    }
    fun goToCreateCategoryFragment() {
        setUpHeader(FRAG_CREATE_CATEGORY)
        addFragment(FRAG_CREATE_CATEGORY, true, null)

    }
    fun goToCreateSupplierFragment() {
        setUpHeader(FRAG_CREATE_SUPPLIER)
        addFragment(FRAG_CREATE_SUPPLIER, true, null)

    }
    fun goToCreatePurchaseFragment() {
        setUpHeader(FRAG_CREATE_PURCHASE)
        addFragment(FRAG_CREATE_PURCHASE, true, null)

    }
    fun goToCreateProductFragment() {
        setUpHeader(FRAG_CREATE_PRODUCT)
        addFragment(FRAG_CREATE_PRODUCT, true, null)

    }
    fun goToUpdateCategoryFragment(categoryType: CategoryType) {
        setUpHeader(FRAG_UPDATE_CATEGORY)
        mFragManager = supportFragmentManager
        // create transaction
        var fragId:Int?=0
        fragId=FRAG_UPDATE_CATEGORY
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

        newFrag = CreateCategoryFragment()
        val b= Bundle()
        b.putParcelable(CategoryType::class.java?.getSimpleName(), categoryType)

        newFrag.setArguments(b)

        mCurrentFrag = newFrag

            fragTransaction!!.setCustomAnimations(
                R.anim.view_transition_in_left,
                R.anim.view_transition_out_left,
                R.anim.view_transition_in_right,
                R.anim.view_transition_out_right
            )

        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

    }
    fun goToNoticeDetailsFragment(notice: Notice) {
        setUpHeader(FRAG_NOTICE_DETAILS)
        mFragManager = supportFragmentManager
        var fragId:Int?=0
        fragId= FRAG_NOTICE_DETAILS
        fragTransaction = mFragManager?.beginTransaction()
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {

        }
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }
        var newFrag: Fragment? = null
        newFrag = NoticeViewFragment()
        val b= Bundle()
        b.putParcelable(Notice::class.java?.getSimpleName(), notice)
        newFrag.setArguments(b)
        mCurrentFrag = newFrag
        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        fragTransaction?.add(R.id.main_container, newFrag!!, fragId.toString())
        fragTransaction?.addToBackStack(fragId.toString())
        fragTransaction!!.commit()

    }
    fun goToUpdateSupplierFragment(supplier: Supplier) {
        setUpHeader(FRAG_UPDATE_CATEGORY)
        mFragManager = supportFragmentManager
        // create transaction
        var fragId:Int?=0
        fragId=FRAG_UPDATE_CATEGORY
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

        newFrag = CreateSupplierFragment()
        val b= Bundle()
        b.putParcelable(Supplier::class.java?.getSimpleName(), supplier)

        newFrag.setArguments(b)

        mCurrentFrag = newFrag

        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

    }
    fun goToUpdatePurchaseFragment(purchase: Purchase) {
        setUpHeader(FRAG_UPDATE_PURCHASE)
        mFragManager = supportFragmentManager
        // create transaction
        var fragId:Int?=0
        fragId=FRAG_UPDATE_PURCHASE
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

        newFrag = CreatePurchaseFragment()
        val b= Bundle()
        b.putParcelable(Purchase::class.java?.getSimpleName(), purchase)

        newFrag.setArguments(b)

        mCurrentFrag = newFrag

        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

    }
    fun goToUpdateProductFragment(product: Product) {
        setUpHeader(FRAG_UPDATE_PRODUCT)
        mFragManager = supportFragmentManager
        // create transaction
        var fragId:Int?=0
        fragId=FRAG_UPDATE_PRODUCT
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

        newFrag = CreateProductFragment()
        val b= Bundle()
        b.putParcelable(Product::class.java?.getSimpleName(), product)

        newFrag.setArguments(b)

        mCurrentFrag = newFrag

        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

    }
    fun goToViewDeliveryFragment(orders: Orders) {
        setUpHeader(FRAG_VIEW_DELIVERY)
        mFragManager = supportFragmentManager
        // create transaction
        var fragId:Int?=0
        fragId=FRAG_VIEW_DELIVERY
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

        newFrag = CreateDeliveryFragment()
        val b= Bundle()
        b.putParcelable(Orders::class.java?.getSimpleName(), orders)

        newFrag.setArguments(b)

        mCurrentFrag = newFrag

        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

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
                newFrag = OrderDeliveryFragment()
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
            FRAG_SUPPLIER-> {
                newFrag = SupplierFragment()
            }
            FRAG_CREATE_SUPPLIER-> {
                newFrag = CreateSupplierFragment()
            }
            FRAG_PURCHASE-> {
                newFrag = PurchaseFragment()
            }
            FRAG_CREATE_PURCHASE-> {
                newFrag = CreatePurchaseFragment()
            }
            FRAG_PRODUCT-> {
                newFrag = ProductFragment()
            }
            FRAG_CREATE_PRODUCT-> {
                newFrag = CreateProductFragment()
            }
            FRAG_NOTICE->{
                newFrag = NoticeFragment()
            }
            FRAG_NEWSFEED->{
                newFrag = NewsfeedFragment()
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
            FRAG_NEWSFEED->{
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.newsfeed)
            }
            FRAG_ORDER -> {
                ll_back_header?.visibility = View.GONE
                rlt_header?.visibility = View.VISIBLE

                tv_title.text = resources.getString(R.string.order)

            }
            FRAG_SETTINGS -> {
                ll_back_header?.visibility = View.GONE
                rlt_header?.visibility = View.GONE
                tv_title.text = resources.getString(R.string.settings)
                btn_footer_settings.setSelected(true)

            }
            FRAG_CATEGORY -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.category)
                btn_footer_store.setSelected(true)

            }
            FRAG_CREATE_SUPPLIER -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.supplier)
                btn_footer_store.setSelected(true)

            }
            FRAG_PURCHASE -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.purchase)
                btn_footer_store.setSelected(true)

            }
            FRAG_CREATE_PURCHASE -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.purchase)
                btn_footer_store.setSelected(true)

            }
            FRAG_VIEW_DELIVERY -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.delivery)
                btn_footer_orders.setSelected(true)

            }
            FRAG_UPDATE_PURCHASE -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.purchase)
                btn_footer_store.setSelected(true)

            }

            FRAG_SUPPLIER -> {
            ll_back_header?.visibility = View.VISIBLE
            rlt_header?.visibility = View.GONE
            tv_details.text = resources.getString(R.string.supplier)
            btn_footer_store.setSelected(true)
        }
            FRAG_PRODUCT -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.product)
                btn_footer_store.setSelected(true)
            }
            FRAG_CREATE_PRODUCT -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.product)
                btn_footer_store.setSelected(true)
            }
            FRAG_UPDATE_PRODUCT-> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.product)
                btn_footer_store.setSelected(true)
            }
            FRAG_NOTICE->{
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.notice)
            }
            FRAG_NOTICE_DETAILS->{
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.details)
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
    fun getVisibleFragments(): Fragment? {
        val fragmentManager = mFragManager
        val fragments = fragmentManager!!.fragments
        fragments.reverse()
        for (fragment in fragments!!) {
            if(fragment is PostBottomsheetFragment ){

                return fragment
            }
        }
        return null
    }
    fun getVisibleFragmentsForComments(): Fragment? {
        val fragmentManager = mFragManager
        val fragments = fragmentManager!!.fragments
        fragments.reverse()
        for (fragment in fragments!!) {
            if(fragment is CommentsFragment ){

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
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.notice)
                

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
    private val CAMERA_PERMISSION_REQUEST_CODE = 1001
    private val RESULT_TAKE_PHOTO = 10
    private val RESULT_LOAD_IMG = 101
    private val REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE = 1002
    private val RESULT_UPDATE_IMAGE = 11

    fun openImageChooser() {
        showImagePickerDialog(this, object :
            DialogActionListener {
            override fun onPositiveClick() {
                openCamera()
            }

            override fun onNegativeClick() {
                checkGalleryPermission()
            }
        })
    }
    private fun checkGalleryPermission() {
        if (isCameraePermissionGranted(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf<String?>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE
                    )
                } else {
                    //start your camera

                    getImageFromGallery()
                }
            } else {
                getImageFromGallery()
            }
        } else {
            //required permission

            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE
            )
        }
    }
    fun openCamera() {
        if (isCameraePermissionGranted(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED&&checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf<String?>(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE),
                        CAMERA_PERMISSION_REQUEST_CODE
                    )
                } else {
                    //start your camera

                    takePhoto()
                }
            } else {
                takePhoto()
            }
        } else {
            //required permission

            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_CAMERA,
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    // Receive the permissions request result
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here

                    takePhoto()
                }
            }
            REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here
                    getImageFromGallery()
                }
            }
        }
    }

    private var mTakeUri: Uri? = null
    private var mFile: File? = null
    private var mCurrentPhotoPath: String? = null
    private fun takePhoto() {
        val intent =
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            mFile = null
            try {
                mFile = createImageFile(this)
                mCurrentPhotoPath = mFile?.getAbsolutePath()
            } catch (ex: IOException) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created


            if (mFile != null) {
                mTakeUri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID, mFile!!
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                } else {
                    val packageManager: PackageManager =
                        this.getPackageManager()
                    val activities: List<ResolveInfo> =
                        packageManager.queryIntentActivities(
                            intent,
                            PackageManager.MATCH_DEFAULT_ONLY
                        )
                    for (resolvedIntentInfo in activities) {
                        val packageName: String? =
                            resolvedIntentInfo.activityInfo.packageName
                        this.grantUriPermission(
                            packageName,
                            mTakeUri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                    }
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mTakeUri)
                startActivityForResult(intent, RESULT_TAKE_PHOTO)
            }
        }
    }

    private fun getImageFromGallery() {
        val photoPickerIntent =
            Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Check which request we're responding to
        Log.e("requestCode", resultCode.toString() + " requestCode" + requestCode)
        Log.e("RESULT_OK", "RESULT_OK" + RESULT_OK)


        when (requestCode) {
            RESULT_LOAD_IMG -> {
                try {
                    val imageUri = data?.data
                    if (imageUri != null) {
                        val file = File(getRealPathFromURI(imageUri, this))
                        goImagePreviewPage(imageUri, file)
                    }
                } catch (e: Exception) {
                    Log.e("exc", "" + e.message)
                    Toast.makeText(this,"Can not found this image", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            RESULT_TAKE_PHOTO -> {
                //return if photopath is null
                if(mCurrentPhotoPath == null)
                    return
                mCurrentPhotoPath = getRightAngleImage(mCurrentPhotoPath!!)
                try {
                    val imgFile = File(mCurrentPhotoPath)
                    if (imgFile.exists()) {
                        goImagePreviewPage(mTakeUri, imgFile)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            RESULT_UPDATE_IMAGE -> {
                if (data != null && data?.hasExtra("updated_image_url")) {
                    val updated_image_url: String? = data?.getStringExtra("updated_image_url")
                    Log.e("updated_image_url", "--$updated_image_url")
                    if (updated_image_url != null) {
                        if (updated_image_url == "") {

                        } else {
                            //update in
                            if ( image_update.equals("profile")){
                                val f = getVisibleFragments()
                                if (f != null) {
                                    if (f is PostBottomsheetFragment) {

                                        f.showImage(updated_image_url)
                                    }

                                }
                            }
                            else{
                                val f = getVisibleFragment()
                                if (f != null) {
                                    if (f is CreateSupplierFragment) {

                                        f.showImage(updated_image_url)
                                    }
                                    else if (f is CreateProductFragment) {

                                        f.showImage(updated_image_url)
                                    }
                                }
                            }




                        }
                    }
                }
            }





        }

    }
    fun goImagePreviewPage(uri: Uri?, imageFile: File) {
        val fileSize = imageFile.length().toInt()
        if (fileSize <= SERVER_SEND_FILE_SIZE_MAX_LIMIT) {
            val i = Intent(
                this,
                ImageUpdateActivity::class.java
            )
            i.putExtra("from", FRAG_CREATE_NEW_DELIVERY)
            temporary_profile_picture = imageFile
            temporary_profile_picture_uri = uri
            startActivityForResult(i, RESULT_UPDATE_IMAGE)
            overridePendingTransition(R.anim.right_to_left, R.anim.stand_by)
        } else {
            showDialogSuccessMessage(
                this,
                resources.getString(R.string.image_size_is_too_large),
                resources.getString(R.string.txt_close),


                null
            )
        }
    }
}
