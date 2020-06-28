package com.evan.bazar.ui.home.product

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.Product
import com.evan.bazar.data.db.entities.Supplier
import com.evan.bazar.ui.home.supplier.ISupplierUpdateListener
import com.evan.bazar.ui.home.supplier.SupplierSearchAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_product_list.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProductSearchAdapter (val context: Context, val product: MutableList<Product>?, val iproductUpdateListener: IProductUpdateListener) : RecyclerView.Adapter<ProductSearchAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_product_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList",""+ Gson().toJson(product));

        return product!!.size
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {


        holder.itemView.text_update.setOnClickListener {
            iproductUpdateListener.onUpdate(product?.get(position)!!)
        }
        Glide.with(context)
            .load(product?.get(position)?.ProductImage)
            .into(holder.itemView.img_image!!)

        var product_name:String=""
        var product_code:String=""
        var sell_price:String=""
        var stock:String=""
        var supplier_price:String=""
        product_name = "<b> <font color=#15507E>Product Name</font> : </b>"+product?.get(position)?.Name
        product_code = "<b> <font color=#15507E>Product Code </font> : </b>"+product?.get(position)?.ProductCode
        sell_price = "<b> <font color=#15507E>Sell Price </font> : </b>"+product?.get(position)?.SellPrice
        stock = "<b> <font color=#15507E>Stock </font> : </b>"+product?.get(position)?.Stock
        supplier_price = "<b> <font color=#15507E>Supplier Price </font> : </b>"+product?.get(position)?.SupplierPrice
        holder.itemView.tv_product_name.text= Html.fromHtml(product_name)
        holder.itemView.tv_product_code.text= Html.fromHtml(product_code)
        holder.itemView.tv_sell_price.text= Html.fromHtml(sell_price)
        holder.itemView.tv_stock.text= Html.fromHtml(stock)
        holder.itemView.tv_supplier_price.text= Html.fromHtml(supplier_price)

        holder.itemView.tv_date.text=getStartDate(product?.get(position)?.Created)

        if (product?.get(position)?.Status==1){
            holder.itemView.text_active.text="Active"
            holder.itemView.text_active?.setTextColor(context?.getColor(R.color.green))
        }
        else{
            holder.itemView.text_active.text="Inactive"
            holder.itemView.text_active?.setTextColor(context?.getColor(R.color.red_marker))

        }


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