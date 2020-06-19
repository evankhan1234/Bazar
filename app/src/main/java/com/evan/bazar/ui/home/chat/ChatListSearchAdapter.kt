package com.evan.bazar.ui.home.chat

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.data.db.entities.Chat
import com.evan.bazar.data.db.entities.ChatList
import com.evan.bazar.ui.home.category.CategoryAdapter
import com.evan.bazar.ui.home.category.ICategoryUpdateListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_chat_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ChatListSearchAdapter (val context: Context, val chat: MutableList<ChatList>?, val iChatViewListener: IChatViewListener) : RecyclerView.Adapter<ChatListSearchAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_chat_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList",""+ Gson().toJson(chat));

        return chat!!.size
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {


        holder.itemView.setOnClickListener {
            iChatViewListener.onUpdate(chat?.get(position)!!)
        }
             Glide.with(context)
                .load(chat?.get(position)!!.Picture)
                .into(holder.itemView?.profile_image)
        holder.itemView.tv_username.text=chat?.get(position)!!.Name
        lastMessage(chat?.get(position)!!.FirebaseId!!,holder.itemView?.tv_last_msg!!)



    }
    var theLastMessage: String? = null
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