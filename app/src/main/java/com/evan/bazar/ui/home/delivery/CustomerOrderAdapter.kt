package com.evan.bazar.ui.home.delivery

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.CustomerOrder
import com.evan.bazar.data.db.entities.Orders
import com.evan.bazar.ui.home.order.IOrderUpdateListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_customer_order_list.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CustomerOrderAdapter (val context: Context, val order: MutableList<CustomerOrder>?, val iCustomerOrderListener: ICustomerOrderListListener, val deleteIdListener:IDeleteIdListener,val itemClickListener:IItemClickListener) : RecyclerView.Adapter<CustomerOrderAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_customer_order_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList",""+ Gson().toJson(order));

        return order!!.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {


        holder.itemView.img_plus.setOnClickListener {
            order?.get(position)?.Quantity= order?.get(position)?.Quantity!!+1
            order?.set(position,order?.get(position))
            itemClickListener?.onClick(order?.get(position)?.Quantity!!,order?.get(position)!!.Price,order?.get(position)!!.Id,1)
        }
        holder.itemView.img_minus.setOnClickListener {
            order?.get(position)?.Quantity= order?.get(position)?.Quantity!!-1
            if(order?.get(position)?.Quantity!!<1){
                order?.get(position)?.Quantity=1
            }
            else{
                order?.set(position,order?.get(position))
                itemClickListener?.onClick(order?.get(position)?.Quantity!!,order?.get(position)!!.Price,order?.get(position)!!.Id,2)
            }

        }
        holder.itemView.btn_delete.setOnClickListener {
            deleteIdListener.onId(order?.get(position)!!.Id!!,order?.get(position)!!.Price,position,order?.get(position)!!.Quantity)
            order?.remove(order?.get(position)!!)
        }
        Glide.with(context)
            .load(order?.get(position)?.Picture)
            .into(holder.itemView.img_image!!)
       holder.itemView.tv_price.setText("Price: "+order?.get(position)?.Price.toString()+" Tk")
        holder.itemView.tv_product_name.setText(order?.get(position)?.Name)
       holder.itemView.text_quantity.setText(order?.get(position)?.Quantity?.toString())
//        var order_address:String=""
//        var order_area:String=""
//
//        order_address = "<b> <font color=#15507E>Order Address</font> : </b>"+order?.get(position)?.OrderAddress
//        order_area = "<b> <font color=#15507E>Order Area </font> : </b>"+order?.get(position)?.OrderArea
//        holder.itemView.tv_order_address.text= Html.fromHtml(order_address)
//        holder.itemView.tv_order_area.text= Html.fromHtml(order_area)
//
        holder.itemView.tv_date.text=getStartDate(order?.get(position)?.Created)


    }
    fun getStartDate(startDate: String?): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatter = SimpleDateFormat("dd,MMMM yyyy")
        val output: String = formatter.format(parser.parse(startDate!!))
        return output
    }
    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}