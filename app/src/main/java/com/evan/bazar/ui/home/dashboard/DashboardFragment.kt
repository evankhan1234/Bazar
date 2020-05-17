package com.evan.bazar.ui.home.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.evan.bazar.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ValueFormatter


class DashboardFragment : Fragment() {

    var pieChart: PieChart?=null
    var chart: BarChart?=null
    var tv_store: TextView?=null
    private val xdata: ArrayList<String>?=arrayListOf()
    private val ydata: ArrayList<Int>?=arrayListOf()
    private val valueSet1: ArrayList<BarEntry>?=arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_dashboard, container, false)
        chart=root?.findViewById(R.id.chart1)
        tv_store=root?.findViewById(R.id.tv_store)
        pieChart=root?.findViewById(R.id.pie)
        tv_store?.isSelected=true
        pieChart?.setDrawHoleEnabled(true)
        pieChart?.setDescription(
            resources.getString(
                R.string.dashboard
            )
        )
        pieChart?.setDescriptionTextSize(13f)
        pieChart?.setRotationEnabled(true)
        pieChart?.setHoleRadius(25f)
        pieChart?.setCenterText(
            resources.getString(
                R.string.dashboard
            )
        )
        pieChart?.setCenterTextSize(10f)
        pieChart?.setTransparentCircleRadius(50f)
        val l: Legend = pieChart?.getLegend()!!
        l.xEntrySpace = 7f
        l.yEntrySpace = 5f
        l.form = Legend.LegendForm.CIRCLE
        l.position = Legend.LegendPosition.RIGHT_OF_CHART
        xdata?.add("Evan")
        xdata?.add("Evan")
        xdata?.add("Evan")
        xdata?.add("Evan")
        ydata?.add(4)
        ydata?.add(5)
        ydata?.add(6)
        ydata?.add(7)
        addDataSet()
        chart!!.setDescription("")
        var data: BarData? = null
        try {
            data = BarData(
                getXAxisValues(),
                getDataSet(
                    10F,
                   10F,
                    10F,
                    200F,
                   25F,
                    36F
                )
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        chart!!.data = data
        chart!!.animateXY(1000, 1000)
        chart!!.invalidate()


        return root
    }
    private fun getXAxisValues(): java.util.ArrayList<String>? {
        val xAxis = java.util.ArrayList<String>()
        xAxis.add("Total")
        xAxis.add("PRESENT")
        xAxis.add("ABSENT")
        xAxis.add("LATE")
        xAxis.add("LEAVE")
        xAxis.add("MOVEMENT")
        return xAxis
    }

    private fun getDataSet(
        Total: Float,
        Present: Float,
        Absent: Float,
        Late: Float,
        Leave: Float,
        Movement: Float
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
        val v2e6 = BarEntry(Movement, 5) // May
        valueSet1.add(v2e6)
        val colors = java.util.ArrayList<Int>()
        colors.add(Color.parseColor("#eab259"))
        colors.add(Color.parseColor("#21a839"))
        colors.add(Color.RED)
        colors.add(Color.BLUE)
        colors.add(Color.parseColor("#1daf89"))
        val barDataSet2 = BarDataSet(valueSet1, " Daily Attendance Chart Values")
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

}
