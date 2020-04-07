package com.evan.bazar.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton
import com.bumptech.glide.Glide

import com.evan.bazar.R
import com.evan.bazar.ui.auth.CreateAccountActivity
import de.hdodenhof.circleimageview.CircleImageView

class StepOneFragment : Fragment() {

    var root:View?=null
    var img_user_add: AppCompatImageButton?=null
    var img_user_profile: CircleImageView?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_step_one, container, false)
        img_user_add=root?.findViewById(R.id.img_user_add)
        img_user_profile=root?.findViewById(R.id.img_user_profile)
        img_user_add?.setOnClickListener{
            (activity as CreateAccountActivity?)!!.openImageChooser()
        }
        return root
    }


    fun showImage(temp:String?){
        Log.e("for","Image"+temp)
        Glide.with(this)
            .load(temp)
            .into(img_user_profile!!)
    }
}
