package com.monoexpensetracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.monoexpensetracker.databinding.FragmentStatisticsBinding
import com.monoexpensetracker.model.MoneyViewModel

class StatisticsFragment : Fragment() {

    private val binding: FragmentStatisticsBinding by lazy {
        FragmentStatisticsBinding.inflate(layoutInflater)
    }
    private lateinit var barChart: BarChart
    private val moneyViewModel: MoneyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        val view = binding.root

        barChart = binding.barchart
        moneyViewModel.expenseList.observe(viewLifecycleOwner)
        {
            displayExpensesByMonth()
        }
        return view
    }

    private fun displayExpensesByMonth() {
        val expensesByMonth = moneyViewModel.getExpenseByMonth()

        Log.d("StatisticsFragment", "Expenses By Month: $expensesByMonth")

        if (expensesByMonth.isEmpty()) {
            Log.d("StatisticsFragment", "No data available for bar chart.")
        } else {
            val entries = ArrayList<BarEntry>()
            val labels = ArrayList<String>()
            var index = 0

            for ((monthYear, totalExpense) in expensesByMonth) {
                Log.d("StatisticsFragment", "Month-Year: $monthYear, Total Expense: $totalExpense")
                entries.add(BarEntry(index.toFloat(), totalExpense.toFloat()))
                labels.add(monthYear)
                index++
            }

            if (entries.isEmpty()) {
                Log.d("StatisticsFragment", "No entries available for bar chart.")
                barChart.clear()
                barChart.invalidate()
                return
            }

            val dataSet = BarDataSet(entries, "Expenses by Month")
            dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

            val barData = BarData(dataSet)
            barChart.data = barData
            barChart.description.text = "Monthly Expenses"
            barChart.setFitBars(true)

            //adding lables to the bars

            val xAxis = barChart.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawLabels(true)
            xAxis.granularity = 1f
            xAxis.labelRotationAngle = 45f
            barChart.invalidate() // Refresh the chart
        }
    }
}