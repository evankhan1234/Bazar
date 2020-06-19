package com.evan.bazar.ui.home.chat

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.ChatList
import com.evan.bazar.ui.home.HomeActivity
import com.evan.bazar.ui.home.category.*
import com.evan.bazar.util.NetworkState
import com.evan.bazar.util.SharedPreferenceUtil
import com.evan.bazar.util.hide
import com.evan.bazar.util.show
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class ChatListFragment : Fragment(),KodeinAware,IChatViewListener,IChatListener {

    override val kodein by kodein()

    private val factory : ChatListModelFactory by instance()

    var chatListAdapter: ChatListAdapter?=null
    var chatListSearchAdapter: ChatListSearchAdapter?=null

    private lateinit var viewModel: ChatListViewModel

    var rcv_chat: RecyclerView?=null
    var rcv_chat_search: RecyclerView?=null
    var progress_bar: ProgressBar?=null
    var edit_content: EditText?=null
    var token:String?=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_chat_list, container, false)
        rcv_chat_search=root?.findViewById(R.id.rcv_chat_search)
        progress_bar=root?.findViewById(R.id.progress_bar)
        rcv_chat=root?.findViewById(R.id.rcv_chat)
        edit_content=root?.findViewById(R.id.edit_content)
        viewModel = ViewModelProviders.of(this, factory).get(ChatListViewModel::class.java)

        viewModel.chatListener=this
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        edit_content?.addTextChangedListener(keyword)
        return root
    }
    fun replace(){
        viewModel.replaceSubscription(this)
        startListening()
    }
    override fun onResume() {
        super.onResume()
        // viewModel.getCategoryType(token!!)
        Log.e("stop","stop")
        initAdapter()
        initState()
        if (activity is HomeActivity) {
            (activity as HomeActivity).onCount()
        }
    }

    private fun initAdapter() {
        chatListAdapter = ChatListAdapter(context!!,this)
        rcv_chat?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rcv_chat?.adapter = chatListAdapter
        startListening()

    }

    private fun startListening() {
        rcv_chat_search?.visibility=View.GONE
        rcv_chat?.visibility=View.VISIBLE

        viewModel.listOfAlerts?.observe(this, Observer {
            chatListAdapter?.submitList(it)
        })

    }


    private fun initState() {
        viewModel.getNetworkState().observe(this, Observer { state ->
            when (state.status) {
                NetworkState.Status.LOADIND -> {
                    progress_bar?.visibility=View.VISIBLE
                }
                NetworkState.Status.SUCCESS -> {
                    progress_bar?.visibility=View.GONE
                }
                NetworkState.Status.FAILED -> {
                    progress_bar?.visibility=View.GONE
                }
            }
        })
    }


    override fun onUpdate(chatList: ChatList) {
        val intent = Intent(activity!!, MessageListActivity::class.java)
        intent.putExtra("userid", chatList.FirebaseId)
        intent.putExtra("name", chatList.Name)
        intent.putExtra("id", chatList.CustomerId)
        activity!!!!.startActivity(intent)
    }

    override fun show(data: MutableList<ChatList>) {
        viewModel.replaceSubscription(this)
        rcv_chat_search?.visibility=View.VISIBLE
        rcv_chat?.visibility=View.GONE
        chatListSearchAdapter = ChatListSearchAdapter(context!!,data!!,this)
        rcv_chat_search?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = chatListSearchAdapter
        }
    }

    override fun started() {
        progress_bar?.show()
    }

    override fun end() {
        progress_bar?.hide()
    }


    var keyword: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            try {
                if (s.toString().equals("")){
                    startListening()
                }
                else{
                    var keyword:String?=""
                    keyword=s.toString()+"%"
                    viewModel.getCategoryType(token!!,keyword)
                }

            } catch (e: Exception) {

            }


        }

    }
    override fun exit(){
        rcv_chat_search?.visibility=View.GONE
        rcv_chat?.visibility=View.GONE
        viewModel.replaceSubscription(this)
    }


}