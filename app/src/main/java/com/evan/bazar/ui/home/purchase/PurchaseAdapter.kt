package com.evan.bazar.ui.home.purchase

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.Purchase

import kotlinx.android.synthetic.main.layout_purchase_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PurchaseAdapter (val context: Context, val iPurchaseUpdateListener: IPurchaseUpdateListener) :
    PagedListAdapter<Purchase, RecyclerView.ViewHolder>(NewsDiffCallback) {
    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlertViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as AlertViewHolder).bind(context, getItem(position), position,iPurchaseUpdateListener)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<Purchase>() {
            override fun areItemsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
                return oldItem.Id == newItem.Id
            }

            override fun areContentsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
                return oldItem == newItem
            }
        }
    }

}

class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(context: Context, purchase: Purchase?, position: Int, listener: IPurchaseUpdateListener) {

        if (purchase != null)
        {

//            itemView.text_update.setOnClickListener {
//                listener.onUpdate(purchase)
//            }
//            Glide.with(context)
//                .load(purchase?.PurchaseImage)
//                .into(itemView.img_icon!!)

            var product_name:String=""
            var purchase_no:String=""
            var item_no:String=""
            var quantity:String=""
            var rate:String=""
            var discount:String=""
            var total:String=""
            var grand_total:String=""
            product_name = "<b> <font color=#15507E>Product Name</font> : </b>"+purchase?.ProductName
             purchase_no = "<b> <font color=#15507E>Purchase No </font> : </b>"+purchase?.PurchaseNo
            item_no = "<b> <font color=#15507E>Item </font> : </b>"+purchase?.Item
            quantity = "<b> <font color=#15507E>Quantity </font> : </b>"+purchase?.Quantity
            rate = "<b> <font color=#15507E>Rate </font> : </b>"+purchase?.Rate
            discount = "<b> <font color=#15507E>Discount </font> : </b>"+purchase?.Discount
            total = "<b> <font color=#15507E>Total </font> : </b>"+purchase?.Total
            grand_total = "<b> <font color=#15507E>Grand Total </font> : </b>"+purchase?.GrandTotal
            itemView.tv_product_name.text= Html.fromHtml(product_name)
            itemView.tv_purchase_no.text= Html.fromHtml(purchase_no)
            itemView.tv_item.text= Html.fromHtml(item_no)
            itemView.tv_quantity.text= Html.fromHtml(quantity)
            itemView.tv_rate.text= Html.fromHtml(rate)
            itemView.tv_discount.text= Html.fromHtml(discount)
            itemView.tv_total.text= Html.fromHtml(total)
            itemView.tv_grand_total.text= Html.fromHtml(grand_total)
//            itemView.text_phone_number.text=purchase?.ContactNumber
//            itemView.text_email.text=purchase?.Email
//            itemView.text_address.text=purchase?.Address
            itemView.tv_date.text=getStartDate(purchase?.PurchaseDate)

            if (purchase?.Status==1){
                itemView.text_active.text="Active"
                itemView.text_active?.setTextColor(context?.getColor(R.color.green))
            }
            else{
                itemView.text_active.text="Inactive"
                itemView.text_active?.setTextColor(context?.getColor(R.color.red_marker))

            }

        }
    }

    companion object {
        fun create(parent: ViewGroup): AlertViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_purchase_list, parent, false)

            return AlertViewHolder(view)
        }
    }
    fun getStartDate(startDate: String?): String? {
        val inputFormat =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val outputFormat =
            DateTimeFormatter.ofPattern("dd,MMMM yyyy")
        return LocalDate.parse(startDate, inputFormat).format(outputFormat)
    }

}