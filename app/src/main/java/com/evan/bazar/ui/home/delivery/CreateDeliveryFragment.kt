package com.evan.bazar.ui.home.delivery

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.evan.bazar.R
import com.evan.bazar.data.db.entities.CustomerOrder
import com.evan.bazar.data.db.entities.Orders
import com.evan.bazar.data.db.entities.ShopUser
import com.evan.bazar.data.network.post.CustomerOrderStatus
import com.evan.bazar.data.network.post.Push
import com.evan.bazar.data.network.post.PushPost
import com.evan.bazar.ui.home.HomeActivity
import com.evan.bazar.ui.home.HomeViewModel
import com.evan.bazar.ui.home.HomeViewModelFactory
import com.evan.bazar.ui.home.dashboard.IPushListener
import com.evan.bazar.ui.home.settings.IShopUserListener
import com.evan.bazar.util.*
import com.google.gson.Gson
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class CreateDeliveryFragment : Fragment(), KodeinAware, ICustomerOrderListListener,
    IDeliveryPostListener, IDeleteListener, IDeleteIdListener, IItemClickListener, IPushListener,
    IShopUserListener, IDeliveryChargeListener {
    override val kodein by kodein()
    var progress_bar: ProgressBar? = null
    var rcv_orders: RecyclerView? = null
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var order: Orders? = null
    var token: String? = ""
    var ordersAdapter: CustomerOrderAdapter? = null
    var btn_ok: Button? = null
    var tv_sub_total: TextView? = null
    var tv_invoice_number: TextView? = null
    var tv_total: TextView? = null
    var tv_grand_total: TextView? = null
    var et_discount: EditText? = null
    var et_paid_amount: EditText? = null
    var et_due_amount: EditText? = null
    var et_delivery_charge: EditText? = null
    var et_delivery_details: EditText? = null
    var root_layout: RelativeLayout? = null
    var btn_cancel: Button? = null
    var pushPost: PushPost? = null
    var push: Push? = null

    //  var data_for: ArrayList<CustomerOrderStatus>?=null
    var data_customer_status: MutableList<CustomerOrderStatus>? = null
    var list: ArrayList<CustomerOrderStatus> = arrayListOf()
    var delivery_charge: Double? = 0.0
    var latitude: Double? = 0.0
    var longitude: Double? = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_create_delivery, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.customerOrderListener = this
        viewModel.deliveryPostListener = this
        viewModel.deleteListener = this
        viewModel.pushListener = this
        viewModel.shopUserListener = this
        viewModel.deliveryChargeListener = this
        btn_cancel = root?.findViewById(R.id.btn_cancel)
        et_due_amount = root?.findViewById(R.id.et_due_amount)
        root_layout = root?.findViewById(R.id.root_layout)
        progress_bar = root?.findViewById(R.id.progress_bar)
        rcv_orders = root?.findViewById(R.id.rcv_orders)
        btn_ok = root?.findViewById(R.id.btn_ok)
        tv_sub_total = root?.findViewById(R.id.tv_sub_total)
        tv_invoice_number = root?.findViewById(R.id.tv_invoice_number)
        tv_grand_total = root?.findViewById(R.id.tv_grand_total)
        tv_total = root?.findViewById(R.id.tv_total)
        et_paid_amount = root?.findViewById(R.id.et_paid_amount)
        et_discount = root?.findViewById(R.id.et_discount)
        et_delivery_details = root?.findViewById(R.id.et_delivery_details)
        et_delivery_charge = root?.findViewById(R.id.et_delivery_charge)
        et_discount?.addTextChangedListener(discount)

        val args: Bundle? = arguments
        if (args != null) {
            if (args?.containsKey(Orders::class.java.getSimpleName()) != null) {
                order = args?.getParcelable(Orders::class.java.getSimpleName())

                Log.e("data", "data" + Gson().toJson(order))
                tv_invoice_number?.text =
                    "Invoice Number : " + getStartDate(order?.Created) + "#" + order?.Id
            }
        }
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        viewModel.getShopUserDetails(token!!)
        viewModel.getToken(token!!, 2, order?.CustomerId!!.toString())
        viewModel.getCustomerOrders(token!!, order?.Id!!)
        viewModel.getDeliveryCharge(token!!)
        btn_cancel?.setOnClickListener {
//
            showDialogReservationConfirmation(context!!,
                "Are you Sure Want to Canceled?",
                "Yes",
                "No",
                object :
                    DialogActionListener {
                    override fun onPositiveClick() {
                        var order_id: Int?
                        order_id = order?.Id
                        viewModel.updateCustomerOrderStatus(token!!, order_id!!, 0)
                        Toast.makeText(activity, "Order Cancel Successfully", Toast.LENGTH_LONG)
                            .show()
                        push = Push("Orders", "Your Order is Cancel")
                        pushPost = PushPost(tokenData, push)
                        viewModel.sendPush(
                            "key=AAAAdCyJ2hw:APA91bGF6x20oQnuC2ZeAXsJju-OCAZ67dBpQvaLx7h18HSAnhl9CPWupCJaV0552qJvm1qIHL_LAZoOvv5oWA9Iraar_XQkWe3JEUmJ1v7iKq09QYyPB3ZGMeSinzC-GlKwpaJU_IvO",
                            pushPost!!
                        )
                        if (activity is HomeActivity) {
                            (activity as HomeActivity).backPress()
                        }
                    }

                    override fun onNegativeClick() {

                    }
                })
        }
        btn_ok?.setOnClickListener {


            var delivery_details: String? = ""
            var paid_amount_text: String? = ""
            var delivery_charge_text: String? = ""

            delivery_details = et_delivery_details?.text.toString()
            paid_amount_text = et_paid_amount?.text.toString()
            delivery_charge_text = et_delivery_charge?.text.toString()

            if (sub_total == 0.0) {
                root_layout?.snackbar("Item is Empty")
            } else {
                var customer_id: Int?
                var order_id: Int?
                var discount: Double? = 0.0
                var paid_amount: Double? = 0.0
                var due_amount: Double? = 0.0

                var invoice_number: String? = ""
                customer_id = order?.CustomerId
                order_id = order?.Id
                invoice_number = getStartDate(order?.Created) + "#" + order?.Id
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                try {
                    discount = et_discount?.text.toString().toDouble()
                } catch (e: Exception) {
                    discount = 0.0
                }
//                try {
//                    due_amount = et_due_amount?.text.toString().toDouble()
//                } catch (e: Exception) {
//                    due_amount = 0.0
//                }
//                paid_amount = et_paid_amount?.text.toString().toDouble()
                // delivery_charge = et_delivery_charge?.text.toString().toDouble()
                viewModel.postDelivery(
                    token!!,
                    customer_id!!,
                    order_id!!,
                    discount!!,
                    total_value!!,
                  0.0,
                    0.0,
                    total_value!!,
                    2,
                    invoice_number!!,
                    currentDate!!,
                    "",
                    delivery_charge!!,
                    latitude!!, longitude!!
                )
                Log.e("sub_total", "sub_total" + sub_total)
                Log.e("customer_id", "customer_id" + customer_id)
                Log.e("order_id", "order_id" + order_id)
                Log.e("discount", "discount" + discount)
                Log.e("grand_total", "grand_total" + total_value)
                Log.e("total", "total" + total_value)
                Log.e("sub_total", "sub_total" + sub_total)
                Log.e("paid_amount", "paid_amount" + paid_amount)
                Log.e("due_amount", "due_amount" + due_amount)
                Log.e("invoice_number", "invoice_number" + invoice_number)
                Log.e("currentDate", "currentDate" + currentDate)
                Log.e("delivery_details", "delivery_details" + delivery_details)
                Log.e("delivery_charge", "delivery_charge" + delivery_charge)
                data_customer_status = list
                Log.e("data_json", "data_json" + Gson().toJson(data_customer_status))
                viewModel.updateCustomerOrderStatus(token!!, order_id!!, 2)
                viewModel.updateOrderDetailsStatus(token!!, data_customer_status)
            }
        }
        return root
    }

    var sub_total: Double? = 0.0
    var total_value: Double? = 0.0

    override fun order(data: MutableList<CustomerOrder>?) {
        var price: Double? = 0.0

        for (i in data?.indices!!) {
            price = price!! + (data?.get(i).Price!! * data?.get(i).Quantity!!)
            var dataa = CustomerOrderStatus(data?.get(i).Id!!, 2)
            list?.add(dataa!!)
        }

        val number2digits: Double = String.format("%.2f", price).toDouble()
        sub_total = number2digits
        total_value = number2digits
        Log.e("Price", "Price" + number2digits)
        tv_sub_total?.text = number2digits?.toString() + " Tk"
        tv_total?.text = number2digits?.toString() + " Tk"
        tv_grand_total?.text = number2digits?.toString() + " Tk"
        ordersAdapter = CustomerOrderAdapter(context!!, data!!, this, this, this)
        rcv_orders?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = ordersAdapter
        }
    }

    fun getStartDate(startDate: String?): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatter = SimpleDateFormat("yyyyMMdd")
        val output: String = formatter.format(parser.parse(startDate!!))
        return output
    }

    override fun show(shopUser: ShopUser?) {
        latitude = shopUser?.Latitude
        longitude = shopUser?.Longitude
    }

    override fun onStarted() {
        progress_bar?.show()
    }

    override fun onEnd() {
        progress_bar?.hide()
    }

    var discount: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            try {

                var discount: Double? = 0.0
                var total: Double? = 0.0
                discount = s.toString().toDouble()
                total = sub_total!! - discount!!
                val number2digits: Double = String.format("%.2f", total).toDouble()
                total_value = number2digits
                tv_total?.setText(number2digits.toString() + " Tk")
                tv_grand_total?.setText(number2digits.toString() + " Tk")

            } catch (e: Exception) {
                val number2digits: Double = String.format("%.2f", sub_total).toDouble()
                total_value = number2digits
                tv_total?.setText(number2digits.toString() + " Tk")
                tv_grand_total?.setText(number2digits.toString() + " Tk")
            }


        }

    }

    override fun show(value: String) {
        push = Push("Orders", "Your Order is processing")
        pushPost = PushPost(tokenData, push)
        viewModel.sendPush(
            "key=AAAAdCyJ2hw:APA91bGF6x20oQnuC2ZeAXsJju-OCAZ67dBpQvaLx7h18HSAnhl9CPWupCJaV0552qJvm1qIHL_LAZoOvv5oWA9Iraar_XQkWe3JEUmJ1v7iKq09QYyPB3ZGMeSinzC-GlKwpaJU_IvO",
            pushPost!!
        )
        Toast.makeText(activity, value, Toast.LENGTH_LONG).show()
        showDialogSuccessfull(context!!,
            value,
            "Ok",
            "Ok",
            object :
                DialogActionListener {
                override fun onPositiveClick() {

                    if (activity is HomeActivity) {
                        (activity as HomeActivity).backPress()
                    }
                }

                override fun onNegativeClick() {

                }
            })

    }

    override fun started() {
        progress_bar?.show()
    }

    override fun end() {
        progress_bar?.hide()
    }

    override fun onStartedView() {
        progress_bar?.show()
    }

    override fun onEndView() {
        progress_bar?.hide()
    }

    override fun onSuccess(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        ordersAdapter?.notifyDataSetChanged()

    }

    override fun onFailure(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun onId(id: Int?, price: Double?, position: Int?, quantity: Int?) {
        data_customer_status?.removeAt(position!!)
        viewModel.deleteCustomerOrderItem(token!!, id!!)

        var discount: Double? = 0.0
        var total: Double? = 0.0
        try {
            discount = et_discount?.text.toString().toDouble()
        } catch (e: Exception) {
            discount = 0.0
        }
        sub_total = sub_total!! - (price!! * quantity!!)
        total = sub_total!! - discount!!
        val number2digitsub: Double = String.format("%.2f", sub_total).toDouble()
        sub_total = number2digitsub
        tv_sub_total?.setText(number2digitsub.toString() + " Tk")
        val number2digits: Double = String.format("%.2f", total).toDouble()
        total_value = number2digits
        tv_total?.setText(number2digits.toString() + " Tk")
        tv_grand_total?.setText(number2digits.toString() + " Tk")
    }

    override fun onClick(item: Int?, pricesProduct: Double?, orderId: Int?, type: Int?) {
        viewModel.updateQuantityStatus(token!!, orderId!!, item!!)
        var total: Double? = 0.0
        var price: Double? = 0.0
        var discount: Double? = 0.0
        try {
            discount = et_discount?.text.toString().toDouble()
        } catch (e: Exception) {
            discount = 0.0
        }
        // price=item!!*pricesProduct!!
        if (type == 1) {
            sub_total = sub_total!! + pricesProduct!!
        } else {
            sub_total = sub_total!! - pricesProduct!!
        }
        // sub_total=sub_total!!+pricesProduct!!
        total = sub_total!! - discount!!
        val number2digitsub: Double = String.format("%.2f", sub_total).toDouble()
        sub_total = number2digitsub
        tv_sub_total?.setText(number2digitsub.toString() + " Tk")
        val number2digits: Double = String.format("%.2f", total).toDouble()
        total_value = number2digits
        tv_total?.setText(number2digits.toString() + " Tk")
        tv_grand_total?.setText(number2digits.toString() + " Tk")
        ordersAdapter?.notifyDataSetChanged()
    }

    var tokenData: String? = ""
    override fun onLoad(data: String) {
        tokenData = data
    }

    override fun onAmount(charge: Double) {
        delivery_charge = charge
    }
}
