package com.evan.bazar.ui.home.supplier

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.Supplier

import com.evan.bazar.ui.home.category.CategoryAdapter
import com.evan.bazar.ui.home.category.ICategoryUpdateListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_supplier_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SupplierAdapter (val context: Context,val iSupplierUpdateListener: ISupplierUpdateListener) :
    PagedListAdapter<Supplier, RecyclerView.ViewHolder>(NewsDiffCallback) {
    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlertViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as AlertViewHolder).bind(context, getItem(position), position,iSupplierUpdateListener)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<Supplier>() {
            override fun areItemsTheSame(oldItem: Supplier, newItem: Supplier): Boolean {
                return oldItem.Id == newItem.Id
            }

            override fun areContentsTheSame(oldItem: Supplier, newItem: Supplier): Boolean {
                return oldItem == newItem
            }
        }
    }

}

class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(context: Context, supplier: Supplier?, position: Int, listener: ISupplierUpdateListener) {

        if (supplier != null)
        {

            itemView.text_update.setOnClickListener {
                listener.onUpdate(supplier)
            }
            Glide.with(context)
                .load(supplier?.SupplierImage)
                .into(itemView.img_icon!!)
            itemView.text_name.text=supplier?.Name
            itemView.text_phone_number.text=supplier?.ContactNumber
            itemView.text_email.text=supplier?.Email
            itemView.text_address.text=supplier?.Address
            itemView.tv_date.text=getStartDate(supplier?.Created)
            // itemView.switch_status.setOnCheckedChangeListener (null)
            //  itemView.switch_status.isChecked = shops?.get(position)?.Status==1
            if (supplier?.Status==1){
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
                .inflate(R.layout.layout_supplier_list, parent, false)

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