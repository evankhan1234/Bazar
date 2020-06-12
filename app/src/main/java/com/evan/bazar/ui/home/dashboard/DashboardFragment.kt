package com.evan.bazar.ui.home.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.evan.bazar.R
import com.evan.bazar.data.db.entities.CustomerOrderCount
import com.evan.bazar.data.db.entities.LastFiveSales
import com.evan.bazar.data.db.entities.Store
import com.evan.bazar.ui.home.HomeViewModel
import com.evan.bazar.ui.home.HomeViewModelFactory
import com.evan.bazar.ui.home.delivery.DeliveryModelFactory
import com.evan.bazar.ui.home.delivery.DeliveryViewModel
import com.evan.bazar.util.SharedPreferenceUtil
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ValueFormatter
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class DashboardFragment : Fragment(),KodeinAware,ILastFiveSalesListener,IStoreCountListener ,ICustomerOrderCountListener{
    override val kodein by kodein()
    var token: String? = ""
    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var pieChart: PieChart?=null
    var chart: BarChart?=null
    var tv_store: TextView?=null
    private val xdata: ArrayList<String>?=arrayListOf()
    private val ydata: ArrayList<Int>?=arrayListOf()
    private val valueSet1: ArrayList<BarEntry>?=arrayListOf()
    var tv_delivered:TextView?=null
    var tv_processing:TextView?=null
    var tv_pending:TextView?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_dashboard, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.lastFiveSalesListener=this
        viewModel.storeCountListener=this
        viewModel.customerOrderCountListener=this
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        viewModel.getStoreCount(token!!)
        viewModel.getLasFive(token!!)
        viewModel.getCustomerOrderCount(token!!)
        chart=root?.findViewById(R.id.chart1)
        tv_store=root?.findViewById(R.id.tv_store)
        pieChart=root?.findViewById(R.id.pie)
        tv_delivered=root?.findViewById(R.id.tv_delivered)
        tv_processing=root?.findViewById(R.id.tv_processing)
        tv_pending=root?.findViewById(R.id.tv_pending)
        tv_store?.isSelected=true
        pieChart?.setDrawHoleEnabled(true)
        pieChart?.setDescription(
            resources.getString(
                R.string.store
            )
        )
        pieChart?.setDescriptionTextSize(13f)
        pieChart?.setRotationEnabled(true)
        pieChart?.setHoleRadius(25f)
        pieChart?.setCenterText(
            resources.getString(
                R.string.store
            )
        )
        pieChart?.setCenterTextSize(10f)
        pieChart?.setTransparentCircleRadius(50f)
        val l: Legend = pieChart?.getLegend()!!
        l.xEntrySpace = 7f
        l.yEntrySpace = 5f
        l.form = Legend.LegendForm.CIRCLE
        l.position = Legend.LegendPosition.RIGHT_OF_CHART

        chart!!.setDescription("")



        return root
    }
    private fun getXAxisValues(): java.util.ArrayList<String>? {
        val xAxis = java.util.ArrayList<String>()
        xAxis.add("First")
        xAxis.add("Second")
        xAxis.add("Third")
        xAxis.add("Fourth")
        xAxis.add("Five")
        return xAxis
    }

    private fun getDataSet(
        Total: Float,
        Present: Float,
        Absent: Float,
        Late: Float,
        Leave: Float
    ): java.util.ArrayList<BarDataSet?>? {
        var dataSets: ArrayList<BarDataSet?>? = null
        val v2e1 = BarEntry(Total, 0) // Feb
        valueSet1!!.add(v2e1)
        val v2e2 = BarEntry(Present, 1) // Feb
        valueSet1.add(v2e2)
        val v2e3 = BarEntry(Absent, 2) // Mar
        valueSet1.add(v2e3)
        val v2e4 = BarEntry(Late, 3) // Apr
        valueSet1.add(v2e4)
        val v2e5 = BarEntry(Leave, 4) // May
        valueSet1.add(v2e5)
        val colors = java.util.ArrayList<Int>()
        colors.add(Color.parseColor("#eab259"))
        colors.add(Color.parseColor("#21a839"))
        colors.add(Color.RED)
        colors.add(Color.BLUE)
        colors.add(Color.parseColor("#1daf89"))
        val barDataSet2 = BarDataSet(valueSet1, " Last Five Sales Statistics")
        barDataSet2.colors = colors
        dataSets = java.util.ArrayList()
        dataSets.add(barDataSet2)
        return dataSets
    }
    private fun addDataSet() {
        val YEntry =
            ArrayList<Entry>()
        for (i in ydata?.indices!!) {
            YEntry.add(
                Entry(
                    ydata?.get(
                        i
                    ).toFloat(), i
                )
            )
        }
        val XEntry = ArrayList<String>()
        for (j in xdata?.indices!!) {
            XEntry.add(xdata.get(j))
        }
        val pie = PieDataSet(YEntry, "")
        pie.sliceSpace = 3f
        pie.selectionShift = 5f
        pie.valueTextSize = 35f
        val colors = ArrayList<Int>()
        colors.add(Color.RED)
        colors.add(Color.parseColor("#21a839"))
        colors.add(Color.parseColor("#eab259"))
        colors.add(Color.DKGRAY)
        pie.colors = colors
        val legend: Legend = pieChart?.getLegend()!!
        legend.form = Legend.LegendForm.CIRCLE
        legend.position = Legend.LegendPosition.RIGHT_OF_CHART
        legend.textSize = 13f
        pie.valueFormatter = MyValueFormatter()
        var pieData: PieData? = null
        try {
            pieData = PieData(xdata, pie)
            pieData!!.setValueTextSize(10f)
            pieData.setValueTextColor(Color.WHITE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        pieChart?.setData(pieData)
        pieChart?.invalidate()
    }

    class MyValueFormatter : ValueFormatter {
        override fun getFormattedValue(value: Float): String {
            return "" + value.toInt()
        }
    }

    override fun onLast(data: MutableList<LastFiveSales>) {

        var first:Float?=0F
        var second:Float?=0F
        var third:Float?=0F
        var fourth:Float?=0F
        var five:Float?=0F
        if (data.size==5){
            first=data.get(0).Total.toFloat()
            second=data.get(1).Total.toFloat()
            third=data.get(2).Total.toFloat()
            fourth=data.get(3).Total.toFloat()
            five=data.get(4).Total.toFloat()
        }
        else if (data.size==4){
            first=data.get(0).Total.toFloat()
            second=data.get(1).Total.toFloat()
            third=data.get(2).Total.toFloat()
            fourth=data.get(3).Total.toFloat()
            five=0F
        }
        else if (data.size==3){
            first=data.get(0).Total.toFloat()
            second=data.get(1).Total.toFloat()
            third=data.get(2).Total.toFloat()
            fourth=0F
            five=0F
        }
        else if (data.size==2){
            first=data.get(0).Total.toFloat()
            second=data.get(1).Total.toFloat()
            third=0F
            fourth=0F
            five=0F
        }
        else if (data.size==1){
            first=data.get(0).Total.toFloat()
            second=0F
            third=0F
            fourth=0F
            five=0F
        }
        else{
            first=0F
            second=0F
            third=0F
            fourth=0F
            five=0F
        }
        var data: BarData? = null
         valueSet1?.clear()
        try {

            data = BarData(
                getXAxisValues(),
                getDataSet(
                    first!!,
                    second!!,
                    third!!,
                    fourth!!,
                    five!!
                )
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        chart!!.data = data
        chart!!.animateXY(1000, 1000)
        chart!!.invalidate()
    }

    override fun onStore(store: Store) {
        xdata?.clear()
        ydata?.clear()
        xdata?.add("Product")
        xdata?.add("Supplier")
        xdata?.add("Purchase")
        xdata?.add("Category")
        ydata?.add(store?.Product!!)
        ydata?.add(store?.Supplier!!)
        ydata?.add(store?.Purchase!!)
        ydata?.add(store?.Category!!)
        addDataSet()
    }

    override fun onCount(customerOrderCount: CustomerOrderCount) {
        tv_pending?.text=customerOrderCount?.Pending?.toString()
        tv_processing?.text=customerOrderCount?.Processing?.toString()
        tv_delivered?.text=customerOrderCount?.Delivered?.toString()
    }

}
