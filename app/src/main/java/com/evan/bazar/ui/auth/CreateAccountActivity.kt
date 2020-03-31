package com.evan.bazar.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.evan.bazar.R
import com.evan.bazar.ui.fragments.StepOneFragment
import com.evan.bazar.ui.fragments.StepTwoFragment

class CreateAccountActivity : AppCompatActivity() {
    var mFragManager: FragmentManager? = null
    var fragTransaction: FragmentTransaction? = null
    var mCurrentFrag: Fragment? = null
    private val FRAG_STEP_ONE: Int = 1
    private val FRAG_STEP_TWO: Int = 2
    var btn_step_1: AppCompatButton ?=null
    var btn_step_2: AppCompatButton ?=null
    var step: Boolean?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        btn_step_1=findViewById(R.id.btn_step_1)
        btn_step_2=findViewById(R.id.btn_step_2)
        addFragment(FRAG_STEP_ONE,false,null)
        btn_step_1?.setOnClickListener{
            goToStepTwoFragment()
            btn_step_2?.visibility=View.VISIBLE

        }
        btn_step_2?.setOnClickListener{
            goToStepOneFragment()

        }
    }
    fun addFragment(fragId: Int, isHasAnimation: Boolean, obj: Any?) {
        // init fragment manager
        mFragManager = supportFragmentManager
        // create transaction
        fragTransaction = mFragManager?.beginTransaction()
        //check if there is any backstack if yes then remove it
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            // mFragManager?.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }

        var newFrag: Fragment? = null
        // identify which fragment will be called
        when (fragId) {
            FRAG_STEP_ONE -> {
                newFrag = StepOneFragment()
            }
            FRAG_STEP_TWO -> {
                newFrag = StepTwoFragment()
            }

        }
        mCurrentFrag = newFrag
        // init argument
        if (obj != null) {
            val args = Bundle()
        }
        // set animation
        if (isHasAnimation) {
            fragTransaction!!.setCustomAnimations(
                R.anim.view_transition_in_left,
                R.anim.view_transition_out_left,
                R.anim.view_transition_in_right,
                R.anim.view_transition_out_right
            )
        }
        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.add(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()
    }

    fun goToStepOneFragment(){
        addFragment(FRAG_STEP_ONE,true,null)
    }
    fun goToStepTwoFragment(){
        addFragment(FRAG_STEP_TWO,true,null)
    }
}
