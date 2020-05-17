package com.evan.bazar.ui.boarding

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.Orders
import com.evan.bazar.ui.home.order.IOrderUpdateListener
import com.evan.bazar.ui.home.order.OrdersAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_slide_item_containter.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SliderAdapter (val context: Context, val order: List<IntroSlide>?) : RecyclerView.Adapter<SliderAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_slide_item_containter, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList",""+ Gson().toJson(order));

        return order!!.size
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {



        Glide.with(context)
            .load(order?.get(position)?.icon)
            .into(holder.itemView.img_name!!)
        holder.itemView.tv_title.setText(order?.get(position)?.title)
        holder.itemView.tv_description.setText(order?.get(position)?.description)


    }
    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}