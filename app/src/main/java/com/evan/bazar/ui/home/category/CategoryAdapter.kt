package com.evan.bazar.ui.home.category

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.CategoryType
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_category_type_list.view.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CategoryAdapter (val context: Context, val shops: MutableList<CategoryType>?,val iCategoryUpdateListener: ICategoryUpdateListener) : RecyclerView.Adapter<CategoryAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_category_type_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList",""+ Gson().toJson(shops));

        return shops!!.size
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {


        holder.itemView.text_update.setOnClickListener {
            iCategoryUpdateListener.onUpdate(shops?.get(position)!!)
        }
//            Glide.with(context)
//                .load(shops?.get(position)?.icon_path)
//                .into( holder.itemView?.img_event_image)
           // val localDateTime: LocalDateTime = LocalDateTime.parse(shops?.get(position)?.created?.substring(0,10))
           // val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
           // val output: String = formatter.format(localDateTime)
            holder.itemView.tv_title.text=shops?.get(position)?.Name
            holder.itemView.tv_date.text=getStartDate(shops?.get(position)?.created)
           // holder.itemView.switch_status.setOnCheckedChangeListener (null)
          //  holder.itemView.switch_status.isChecked = shops?.get(position)?.Status==1
            if (shops?.get(position)?.Status==1){
                holder.itemView.tv_status.text="Active"
                holder.itemView.tv_status?.setTextColor(context?.getColor(R.color.green))
            }
            else{
                holder.itemView.tv_status.text="Inactive"
                holder.itemView.tv_status?.setTextColor(context?.getColor(R.color.red_marker))

            }

            holder.itemView.setOnClickListener {
                //holder.bind(delivery,position, onItemClickListener);



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