package com.evan.bazar.ui.home.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import com.evan.bazar.R
import com.evan.bazar.data.db.entities.ShopUser
import com.evan.bazar.ui.auth.LoginActivity
import com.evan.bazar.ui.home.HomeActivity
import com.evan.bazar.ui.home.HomeViewModel
import com.evan.bazar.ui.home.HomeViewModelFactory
import com.evan.bazar.util.SharedPreferenceUtil
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.layout_supplier_list.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SettingsFragment : Fragment(),KodeinAware,IShopUserListener {

    override val kodein by kodein()

    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var progress_circular:ProgressBar?=null
    var tv_name:TextView?=null
    var tv_email:TextView?=null
    var tv_mobile:TextView?=null
    var tv_date:TextView?=null
    var tv_shop_name:TextView?=null
    var tv_license:TextView?=null
    var tv_address:TextView?=null
    var tv_owner_address:TextView?=null
    var img_avatar:CircleImageView?=null
    var btn_log_out:Button?=null
    var btn_change_profile:Button?=null
    var token:String?=""
    var shopUsers: ShopUser?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_settings, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.shopUserListener=this
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        viewModel.getShopUserDetails(token!!)
        progress_circular=root?.findViewById(R.id.progress_circular)
        tv_name=root?.findViewById(R.id.tv_name)
        tv_owner_address=root?.findViewById(R.id.tv_owner_address)
        btn_log_out=root?.findViewById(R.id.btn_log_out)
        tv_email=root?.findViewById(R.id.tv_email)
        tv_mobile=root?.findViewById(R.id.tv_mobile)
        tv_date=root?.findViewById(R.id.tv_date)
        tv_shop_name=root?.findViewById(R.id.tv_shop_name)
        tv_license=root?.findViewById(R.id.tv_license)
        tv_address=root?.findViewById(R.id.tv_address)
        img_avatar=root?.findViewById(R.id.img_avatar)
        btn_change_profile=root?.findViewById(R.id.btn_change_profile)
        btn_log_out?.setOnClickListener {
            Toast.makeText(context!!,"Successfully Logout", Toast.LENGTH_SHORT).show()
            SharedPreferenceUtil.saveShared(context!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN, "")
            SharedPreferenceUtil.saveShared(context!!, SharedPreferenceUtil.TYPE_FRESH, "")
            val intent= Intent(context!!, LoginActivity::class.java)
            startActivity(intent)
            if (activity is HomeActivity) {
                (activity as HomeActivity).finishs()
            }
        }
        btn_change_profile?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToUpdateProfileFragment(shopUsers!!)
            }
        }
        return root
    }

    override fun show(shopUser: ShopUser?) {
        shopUsers=shopUser
        Log.e("shopUser","shopUser"+Gson().toJson(shopUser))
        tv_name?.text=shopUser?.OwnerName
        tv_email?.text=shopUser?.Email
        tv_mobile?.text=shopUser?.OwnerMobileNumber
        tv_shop_name?.text=shopUser?.Name
        tv_license?.text=shopUser?.LicenseNumber
        tv_address?.text=shopUser?.Address
        tv_owner_address?.text=shopUser?.OwnerAddress
        tv_date?.text=getStartDate(shopUser?.AgreementDate)
        Glide.with(context!!)
            .load(shopUser?.Picture)
            .into(img_avatar!!)
    }

    override fun onStarted() {
        progress_circular?.visibility=View.VISIBLE
    }

    override fun onEnd() {
        progress_circular?.visibility=View.GONE
    }
    fun getStartDate(startDate: String?): String? {
        val inputFormat =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val outputFormat =
            DateTimeFormatter.ofPattern("dd,MMMM yyyy")
        return LocalDate.parse(startDate, inputFormat).format(outputFormat)
    }
}
