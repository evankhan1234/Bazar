package com.evan.bazar.ui.home.chat

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.Chat
import com.evan.bazar.data.db.entities.FIrebaseUsers
import com.evan.bazar.data.network.post.Push
import com.evan.bazar.data.network.post.PushPost
import com.evan.bazar.ui.home.HomeViewModel
import com.evan.bazar.ui.home.HomeViewModelFactory
import com.evan.bazar.ui.home.dashboard.IPushListener
import com.evan.bazar.util.SharedPreferenceUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.ArrayList
import java.util.HashMap

class MessageListActivity : AppCompatActivity(),KodeinAware ,IPushListener{

    var profile_image: CircleImageView? = null
    var username: TextView? = null

    var fuser: FirebaseUser? = null
    var reference: DatabaseReference? = null

    var btn_send: ImageButton? = null
    var text_send: EditText? = null

    var messageAdapter: MessageAdapter? = null
    var mchat: MutableList<Chat?>? = null

    var recyclerView: RecyclerView? = null



    var seenListener: ValueEventListener? = null

    var userid: String? = null
    var name: String? = null
    var id: String? = null
    var token: String? = ""
    override val kodein by kodein()
    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var pushPost: PushPost?=null
    var push: Push?=null
    var notify = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.pushListener=this
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setTitle("")
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { // and this
            finish()
        }
        token = SharedPreferenceUtil.getShared(this, SharedPreferenceUtil.TYPE_AUTH_TOKEN)


        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView!!.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(getApplicationContext())
        linearLayoutManager.stackFromEnd = true
        recyclerView!!.layoutManager = linearLayoutManager
        profile_image = findViewById<CircleImageView>(R.id.profile_image)
        username = findViewById<TextView>(R.id.username)
        btn_send = findViewById<ImageButton>(R.id.btn_send)
        text_send = findViewById<EditText>(R.id.text_send)
        intent = getIntent()
        userid = intent!!.getStringExtra("userid")
        name = intent!!.getStringExtra("name")
        id = intent!!.getStringExtra("id")
        viewModel.updateChatCount(token!!,id!!.toInt())
        viewModel.getToken(token!!,2,id!!)

        username!!.setText(name)
        fuser = FirebaseAuth.getInstance().currentUser
        val data = fuser!!.uid
        btn_send!!.setOnClickListener {
            notify = true
            val msg = text_send!!.text.toString()
            if (msg != "") {
                Log.e("data","sender"+fuser!!.uid)
                Log.e("data","receiver"+userid!!)
                sendMessage(fuser!!.uid, userid!!, msg)
            } else {
                Toast.makeText(
                    this@MessageListActivity,
                    "You can't send empty message",
                    Toast.LENGTH_SHORT
                ).show()
            }
            text_send!!.setText("")
        }
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid!!)
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user: FIrebaseUsers? = dataSnapshot.getValue(FIrebaseUsers::class.java)
                // username!!.setText(user!!.username)
                if (user?.imageURL.equals("default")) {
                    profile_image!!.setImageResource(R.mipmap.ic_launcher)
                } else {
                    //and this
                    Glide.with(getApplicationContext()).load(user?.imageURL).into(profile_image!!)
                }
                readMesagges(fuser!!.uid, userid!!, user?.imageURL!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        seenMessage(userid!!)
    }

    open fun seenMessage(userid: String) {
        reference = FirebaseDatabase.getInstance().getReference("Chats")
        seenListener = reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val chat: Chat? = snapshot.getValue(Chat::class.java)
                    if (chat!!.receiver.equals(fuser!!.uid) && chat.sender
                            .equals(userid)
                    ) {
                        val hashMap =
                            HashMap<String, Any>()
                        hashMap["isseen"] = true
                        snapshot.ref.updateChildren(hashMap)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    open fun sendMessage(
        sender: String,
        receiver: String,
        message: String
    ) {
        var reference = FirebaseDatabase.getInstance().reference
        val hashMap =
            HashMap<String, Any>()
        hashMap["sender"] = sender
        hashMap["receiver"] = receiver
        hashMap["message"] = message
        hashMap["isseen"] = false
        reference.child("Chats").push().setValue(hashMap)


        // add user to chat fragment
        val chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
            .child(fuser!!.uid)
            .child(userid!!)
        chatRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    chatRef.child("id").setValue(userid)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        val chatRefReceiver =
            FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(userid!!)
                .child(fuser!!.uid)
        chatRefReceiver.child("id").setValue(fuser!!.uid)
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser!!.uid)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (notify) {
                    push= Push("New Message",message)
                    pushPost= PushPost(tokenData,push)
                    viewModel.sendPush("key=AAAAdCyJ2hw:APA91bGF6x20oQnuC2ZeAXsJju-OCAZ67dBpQvaLx7h18HSAnhl9CPWupCJaV0552qJvm1qIHL_LAZoOvv5oWA9Iraar_XQkWe3JEUmJ1v7iKq09QYyPB3ZGMeSinzC-GlKwpaJU_IvO",pushPost!!)
                }
                notify = false
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

//    open fun sendNotifiaction(
//        receiver: String,
//        username: String,
//        message: String
//    ) {
//        val tokens = FirebaseDatabase.getInstance().getReference("Tokens")
//        val query = tokens.orderByKey().equalTo(receiver)
//        query.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (snapshot in dataSnapshot.children) {
//                    val token: Token? = snapshot.getValue(Token::class.java)
//                    val data = Data(
//                        fuser!!.uid,
//                        R.mipmap.ic_launcher,
//                        "$username: $message",
//                        "New Message",
//                        userid
//                    )
//                    val sender = Sendet(data, token!!.token)
//                    apiService!!.sendNotification(sender)!!
//                        .enqueue(object : Callback<MyResponse> {
//                            override fun onResponse(
//                                call: Call<MyResponse>,
//                                response: Response<MyResponse>
//                            ) {
//                                if (response.code() == 200) {
//                                    if (response.body()!!.success !== 1) {
//                                        Toast.makeText(
//                                            this@MessageActivity,
//                                            "Failed!",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
//                                }
//                            }
//
//                            override fun onFailure(
//                                call: Call<MyResponse>,
//                                t: Throwable
//                            ) {
//                            }
//                        })
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })
//    }

    open fun readMesagges(
        myid: String,
        userid: String,
        imageurl: String
    ) {
        mchat = ArrayList<Chat?>()
        reference = FirebaseDatabase.getInstance().getReference("Chats")
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mchat!!.clear()
                for (snapshot in dataSnapshot.children) {
                    val chat: Chat? = snapshot.getValue(Chat::class.java)
                    if (chat!!.receiver.equals(myid) && chat!!.sender.equals(userid) ||
                        chat.receiver.equals(userid) && chat.sender.equals(myid)
                    ) {
                        mchat!!.add(chat)
                    }
                    messageAdapter = MessageAdapter(this@MessageListActivity, mchat, imageurl)
                    recyclerView!!.adapter = messageAdapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    open fun currentUser(userid: String?) {
        val editor: SharedPreferences.Editor =
            getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
        editor.putString("currentuser", userid)
        editor.apply()
    }

    open fun status(status: String) {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser!!.uid)
        val hashMap =
            HashMap<String, Any>()
        hashMap["status"] = status
        reference!!.updateChildren(hashMap)
    }

    override fun onResume() {
        super.onResume()
        status("online")
        currentUser(userid)
    }

    override  fun onPause() {
        super.onPause()
        reference!!.removeEventListener(seenListener!!)
        status("offline")
        currentUser("none")
    }

    var tokenData:String?=""
    override fun onLoad(data: String) {
        tokenData=data
    }
}


