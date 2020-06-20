package com.evan.bazar.ui.home.chat

import android.annotation.SuppressLint
import android.content.Context
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

import com.evan.bazar.ui.home.category.ICategoryUpdateListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.layout_chat_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
            Glide.with(context)
                .load(chatList?.Picture)
                .into(itemView?.profile_image)
            itemView.tv_username.text = chatList!!.Name
            itemView.setOnClickListener {
                listener.onUpdate(chatList)
            }
            lastMessage(chatList.FirebaseId!!, itemView.tv_last_msg)
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

    open fun lastMessage(userid: String, last_msg: TextView) {

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