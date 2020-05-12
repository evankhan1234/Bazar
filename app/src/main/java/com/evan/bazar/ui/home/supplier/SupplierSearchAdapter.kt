package com.evan.bazar.ui.home.supplier

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.data.db.entities.Supplier
import com.evan.bazar.ui.home.category.CategoryAdapter
import com.evan.bazar.ui.home.category.ICategoryUpdateListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_supplier_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SupplierSearchAdapter (val context: Context, val supplier: MutableList<Supplier>?, val iCategoryUpdateListener: ISupplierUpdateListener) : RecyclerView.Adapter<SupplierSearchAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_supplier_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList",""+ Gson().toJson(supplier));

        return supplier!!.size
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {


        holder.itemView.text_update.setOnClickListener {
            iCategoryUpdateListener.onUpdate(supplier?.get(position)!!)
        }
        Glide.with(context)
            .load(supplier?.get(position)?.SupplierImage)
            .into(holder.itemView.img_icon!!)
        holder.itemView.text_name.text=supplier?.get(position)?.Name
        holder.itemView.text_phone_number.text=supplier?.get(position)?.ContactNumber
        holder.itemView.text_email.text=supplier?.get(position)?.Email
        holder.itemView.text_address.text=supplier?.get(position)?.Address
        holder.itemView.tv_date.text=getStartDate(supplier?.get(position)?.Created)
        // holder.itemView.switch_status.setOnCheckedChangeListener (null)
        //  holder.itemView.switch_status.isChecked = shops?.get(position)?.Status==1
        if (supplier?.get(position)?.Status==1){
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