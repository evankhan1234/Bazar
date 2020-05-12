package com.evan.bazar.ui.home.purchase

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.Purchase
import com.evan.bazar.ui.home.category.CategoryAdapter
import com.evan.bazar.ui.home.category.ICategoryUpdateListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_purchase_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PurchaseSearchAdapter (val context: Context, val purchase: MutableList<Purchase>?, val iPurchaseUpdateListener: IPurchaseUpdateListener) : RecyclerView.Adapter<PurchaseSearchAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_purchase_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList",""+ Gson().toJson(purchase));

        return purchase!!.size
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {


        holder.itemView.text_update.setOnClickListener {
            iPurchaseUpdateListener.onUpdate(purchase?.get(position)!!)
        }
//            Glide.with(context)
//                .load(purchase?.PurchaseImage)
//                .into(holder.itemView.img_icon!!)

        var product_name:String=""
        var purchase_no:String=""
        var item_no:String=""
        var quantity:String=""
        var rate:String=""
        var discount:String=""
        var total:String=""
        var grand_total:String=""
        product_name = "<b> <font color=#15507E>Product Name</font> : </b>"+purchase?.get(position)?.ProductName
        purchase_no = "<b> <font color=#15507E>Purchase No </font> : </b>"+purchase?.get(position)?.PurchaseNo
        item_no = "<b> <font color=#15507E>Item </font> : </b>"+purchase?.get(position)?.Item
        quantity = "<b> <font color=#15507E>Quantity </font> : </b>"+purchase?.get(position)?.Quantity
        rate = "<b> <font color=#15507E>Rate </font> : </b>"+purchase?.get(position)?.Rate
        discount = "<b> <font color=#15507E>Discount </font> : </b>"+purchase?.get(position)?.Discount
        total = "<b> <font color=#15507E>Total </font> : </b>"+purchase?.get(position)?.Total
        grand_total = "<b> <font color=#15507E>Grand Total </font> : </b>"+purchase?.get(position)?.GrandTotal
        holder.itemView.tv_product_name.text= Html.fromHtml(product_name)
        holder.itemView.tv_purchase_no.text= Html.fromHtml(purchase_no)
        holder.itemView.tv_item.text= Html.fromHtml(item_no)
        holder.itemView.tv_quantity.text= Html.fromHtml(quantity)
        holder.itemView.tv_rate.text= Html.fromHtml(rate)
        holder.itemView.tv_discount.text= Html.fromHtml(discount)
        holder.itemView.tv_total.text= Html.fromHtml(total)
        holder.itemView.tv_grand_total.text= Html.fromHtml(grand_total)
//            holder.itemView.text_phone_number.text=purchase?.ContactNumber
//            holder.itemView.text_email.text=purchase?.Email
//            holder.itemView.text_address.text=purchase?.Address
        holder.itemView.tv_date.text=getStartDate(purchase?.get(position)?.PurchaseDate)

        if (purchase?.get(position)?.Status==1){
            holder.itemView.text_active.text="Active"
            holder.itemView.text_active?.setTextColor(context?.getColor(R.color.green))
        }
        else{
            holder.itemView.text_active.text="Inactive"
            holder.itemView.text_active?.setTextColor(context?.getColor(R.color.red_marker))

        }


    }
    fun getStartDate(startDate: String?): String? {
        val inputFormat =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val outputFormat =
            DateTimeFormatter.ofPattern("dd,MMMM yyyy")
        return LocalDate.parse(startDate, inputFormat).format(outputFormat)
    }
    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}