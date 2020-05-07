package com.evan.bazar.ui.home.category

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.ui.home.HomeActivity
import com.evan.bazar.ui.home.HomeViewModel
import com.evan.bazar.ui.home.HomeViewModelFactory
import com.evan.bazar.util.SharedPreferenceUtil
import com.evan.bazar.util.hide
import com.evan.bazar.util.show
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class CategoryFragment : Fragment() , KodeinAware,ICategoryListener {
    override val kodein by kodein()

    private val factory : HomeViewModelFactory by instance()

    var categoryAdapter:CategoryAdapter?=null

    private lateinit var viewModel: HomeViewModel

    var rcv_category:RecyclerView?=null
    var progress_bar:ProgressBar?=null
    var btn_category_new:ImageView?=null
    var token:String?=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_category, container, false)
        progress_bar=root?.findViewById(R.id.progress_bar)
        rcv_category=root?.findViewById(R.id.rcv_category)
        btn_category_new=root?.findViewById(R.id.btn_category_new)
         viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)

        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)

        viewModel.categoryListener=this

        btn_category_new?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToCreateCategoryFragment()
            }
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCategoryType(token!!)
        Log.e("stop","stop")
    }



    fun removeChild() {
        val f =
            childFragmentManager.findFragmentByTag(CreateCategoryFragment::class.java.simpleName)
        val f1 = childFragmentManager.findFragmentByTag(CreateCategoryFragment::class.java.simpleName)
        if (f != null) {
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right)
            transaction.remove(f)
            transaction.commit()
            childFragmentManager.popBackStack()
        }
    }

    override fun show(data: MutableList<CategoryType>) {
        categoryAdapter = CategoryAdapter(context!!,data!!)
        rcv_category?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = categoryAdapter
        }
    }

    override fun started() {
        progress_bar?.show()
    }

    override fun end() {
        progress_bar?.hide()
    }


}
