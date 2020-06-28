package com.evan.bazar.ui.home.product

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
import com.evan.bazar.data.db.entities.Product
import com.evan.bazar.ui.home.product.IProductUpdateListener
import kotlinx.android.synthetic.main.layout_product_list.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProductAdapter (val context: Context, val iProductUpdateListener: IProductUpdateListener) :
    PagedListAdapter<Product, RecyclerView.ViewHolder>(NewsDiffCallback) {
    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlertViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as AlertViewHolder).bind(context, getItem(position), position,iProductUpdateListener)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.Id == newItem.Id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

}

class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(context: Context, product: Product?, position: Int, listener: IProductUpdateListener) {

        if (product != null)
        {

            itemView.text_update.setOnClickListener {
                listener.onUpdate(product)
            }
            Glide.with(context)
                .load(product?.ProductImage)
                .into(itemView.img_image!!)

            var product_name:String=""
            var product_code:String=""
            var sell_price:String=""
            var stock:String=""
            var supplier_price:String=""
//            var discount:String=""
//            var total:String=""
//            var grand_total:String=""
            product_name = "<b> <font color=#15507E>Product Name</font> : </b>"+product?.Name
            product_code = "<b> <font color=#15507E>Product Code </font> : </b>"+product?.ProductCode
            sell_price = "<b> <font color=#15507E>Sell Price </font> : </b>"+product?.SellPrice
            stock = "<b> <font color=#15507E>Stock </font> : </b>"+product?.Stock
            supplier_price = "<b> <font color=#15507E>Supplier Price </font> : </b>"+product?.SupplierPrice
//            discount = "<b> <font color=#15507E>Discount </font> : </b>"+Product?.Discount
//            total = "<b> <font color=#15507E>Total </font> : </b>"+Product?.Total
//            grand_total = "<b> <font color=#15507E>Grand Total </font> : </b>"+Product?.GrandTotal
              itemView.tv_product_name.text= Html.fromHtml(product_name)
              itemView.tv_product_code.text= Html.fromHtml(product_code)
              itemView.tv_sell_price.text= Html.fromHtml(sell_price)
              itemView.tv_stock.text= Html.fromHtml(stock)
              itemView.tv_supplier_price.text= Html.fromHtml(supplier_price)
//            itemView.tv_discount.text= Html.fromHtml(discount)
//            itemView.tv_total.text= Html.fromHtml(total)
//            itemView.tv_grand_total.text= Html.fromHtml(grand_total)
////            itemView.text_phone_number.text=Product?.ContactNumber
////            itemView.text_email.text=Product?.Email
////            itemView.text_address.text=Product?.Address
            itemView.tv_date.text=getStartDate(product?.Created)

            if (product?.Status==1){
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
                .inflate(R.layout.layout_product_list, parent, false)

            return AlertViewHolder(view)
        }
    }
    fun getStartDate(startDate: String?): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatter = SimpleDateFormat("dd,MMMM yyyy")
        val output: String = formatter.format(parser.parse(startDate!!))
        return output
    }

}