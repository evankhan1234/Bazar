package com.evan.bazar.ui.home.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.CategoryType
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.synthetic.main.layout_category_type_list.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CategoryListAdapter (val context: Context,val iCategoryUpdateListener: ICategoryUpdateListener) :
    PagedListAdapter<CategoryType, RecyclerView.ViewHolder>(NewsDiffCallback) {
    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlertViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as AlertViewHolder).bind(context, getItem(position), position,iCategoryUpdateListener)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<CategoryType>() {
            override fun areItemsTheSame(oldItem: CategoryType, newItem: CategoryType): Boolean {
                return oldItem.Id == newItem.Id
            }

            override fun areContentsTheSame(oldItem: CategoryType, newItem: CategoryType): Boolean {
                return oldItem == newItem
            }
        }
    }

}

class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(context: Context, categoryType: CategoryType?, position: Int,listener: ICategoryUpdateListener) {
//        if (position == 0)
//            itemView.empty_view.visibility = View.VISIBLE
//        itemView.tv_date.text = CategoryType?.date?.replace("-",".")
//        itemView.tv_title.text = CategoryType?.title
//        itemView.tv_decription.text = CategoryType?.body
        if (categoryType != null)
        {

            itemView.text_update.setOnClickListener {
                listener.onUpdate(categoryType)
            }
            itemView.tv_title.text=categoryType?.Name
            itemView.tv_date.text=getStartDate(categoryType?.created)
            // itemView.switch_status.setOnCheckedChangeListener (null)
            //  itemView.switch_status.isChecked = shops?.get(position)?.Status==1
            if (categoryType?.Status==1){
                itemView.tv_status.text="Active"
                itemView.tv_status?.setTextColor(context?.getColor(R.color.green))
            }
            else{
                itemView.tv_status.text="Inactive"
                itemView.tv_status?.setTextColor(context?.getColor(R.color.red_marker))

            }

        }
    }

    companion object {
        fun create(parent: ViewGroup): AlertViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_category_type_list, parent, false)

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