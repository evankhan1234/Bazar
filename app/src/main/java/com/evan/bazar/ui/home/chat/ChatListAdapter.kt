package com.evan.bazar.ui.home.chat

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.Chat
import com.evan.bazar.data.db.entities.ChatList

import com.google.firebase.auth.FirebaseAuth
import com.evan.bazar.data.db.entities.FIrebaseUsers
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.layout_chat_list.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

class ChatListAdapter (val context: Context,val iChatViewListener: IChatViewListener) :
    PagedListAdapter<ChatList, RecyclerView.ViewHolder>(NewsDiffCallback) {
    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlertViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as AlertViewHolder).bind(context, getItem(position), position,iChatViewListener)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<ChatList>() {
            override fun areItemsTheSame(oldItem: ChatList, newItem: ChatList): Boolean {
                return oldItem.Id == newItem.Id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ChatList, newItem: ChatList): Boolean {
                return oldItem == newItem
            }
        }
    }

}

class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(context: Context, chatList: ChatList?, position: Int, listener: IChatViewListener) {
//        if (position == 0)
//            itemView.empty_view.visibility = View.VISIBLE
//        itemView.tv_date.text = CategoryType?.date?.replace("-",".")
//        itemView.tv_title.text = CategoryType?.title
//        itemView.tv_decription.text = CategoryType?.body
        if (chatList != null) {
            try {
                val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val past: Date = format.parse(chatList?.Created)
                val currentDate = format.format(Date())
                val now: Date = format.parse(currentDate)

                val seconds: Long =
                    TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime())
                val minutes: Long =
                    TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime())
                val hours: Long =
                    TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime())
                val days: Long = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime())
                if (seconds < 60) {
                    Log.e("ago","ago"+seconds+" seconds ago")
                    itemView.tv_ago.text=seconds.toString()+" seconds ago"
                } else if (minutes < 60) {
                    println("$minutes minutes ago")
                    Log.e("ago","ago"+minutes+" minutes ago")
                    itemView.tv_ago.text=minutes.toString()+" minutes ago"
                } else if (hours < 24) {
                    println("$hours hours ago")
                    Log.e("ago","ago"+hours+" hours ago")
                    itemView.tv_ago.text=hours.toString()+" hours ago"
                } else {
                    println("$days days ago")
                    Log.e("ago","ago"+days+" days ago")
                    itemView.tv_ago.text=days.toString()+"  days ago"
                }
            } catch (j: Exception) {
                j.printStackTrace()
            }
            Glide.with(context)
                .load(chatList?.Picture)
                .into(itemView?.profile_image)
            itemView.tv_username.text = chatList!!.Name
            itemView.setOnClickListener {
                listener.onUpdate(chatList)
            }
            lastMessage(chatList.FirebaseId!!, itemView.tv_last_msg,context)
            val reference = FirebaseDatabase.getInstance().getReference("Users").child(chatList.FirebaseId!!)
            reference!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val user: FIrebaseUsers? = dataSnapshot.getValue(FIrebaseUsers::class.java)
                    if (user?.status.equals("online")) {
                        itemView.img_on.setVisibility(View.VISIBLE)
                        itemView.img_off.setVisibility(View.GONE)
                    } else {
                        itemView.img_on.setVisibility(View.GONE)
                        itemView.img_off.setVisibility(View.VISIBLE)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

        }
    }

    companion object {
        fun create(parent: ViewGroup): AlertViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_chat_list, parent, false)

            return AlertViewHolder(view)
        }
    }
    var theLastMessage: String? = null
    fun getStartDate(startDate: String?): String? {
        val inputFormat =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val outputFormat =
            DateTimeFormatter.ofPattern("dd,MMMM yyyy")
        return LocalDate.parse(startDate, inputFormat).format(outputFormat)
    }


    open fun lastMessage(userid: String, last_msg: TextView,context: Context) {

        theLastMessage = "default"
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().getReference("Chats")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val chat: Chat? = snapshot.getValue(Chat::class.java)
                    if (firebaseUser != null && chat != null) {
                        if (chat.receiver.equals(firebaseUser.uid) && chat.sender
                                .equals(userid) ||
                            chat.receiver.equals(userid) && chat.sender
                                .equals(firebaseUser.uid)
                        ) {
                            if(chat.isseen!!){
                                last_msg.setTextColor(context.getColor(R.color.black_opacity_60))
                            }
                            else{
                                last_msg.setTextColor(context.getColor(R.color.colorPrimary))
                            }
                            theLastMessage = chat.message
                        }
                    }
                }
                when (theLastMessage) {
                    "default" -> last_msg.text = "No Message"
                    else -> last_msg.text = theLastMessage
                }
                theLastMessage = "default"
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}