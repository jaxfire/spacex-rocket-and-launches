package com.jaxfire.spacexinfo.ui.rocket_info.detail

import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jaxfire.spacexinfo.R
import com.jaxfire.spacexinfo.data.db.entity.LaunchEntity
import com.jaxfire.spacexinfo.data.repository.SpaceXInfoRepository
import com.jaxfire.spacexinfo.internal.ChartValueFormatter
import com.jaxfire.spacexinfo.internal.lazyDeferred

class RocketDetailViewModel(
    private val spaceXInfoRepository: SpaceXInfoRepository,
    private val rocketId: String
) : ViewModel() {

    val launches by lazyDeferred {
        spaceXInfoRepository.getLaunchesForRocket(rocketId)
    }

    val rocket by lazyDeferred {
        spaceXInfoRepository.getRocket(rocketId)
    }

    val isDownloading = spaceXInfoRepository.isDownloading()

    fun updateChart(launchData: List<LaunchEntity>, lineChart: LineChart) {

        val listOfYears = launchData.map { it.launchYear }.toMutableList()
        val yearsCount = mutableMapOf<String, Int>()
        val listOfValidYears = listOfYears.filter { yearStr -> yearStr.toFloatOrNull() != null }
        listOfValidYears.forEach { year -> yearsCount[year] = listOfYears.count { it == year } }
        val entries = mutableListOf<Entry>()
        yearsCount.forEach { (key, value) -> entries.add(Entry(key.toFloat(), value.toFloat())) }

        val dataSet = LineDataSet(entries, "Label")

        // Chart visual customisations
        dataSet.valueFormatter = ChartValueFormatter()
        dataSet.valueTextSize = 12f
        dataSet.color = R.color.space_x_blue
        val lineData = LineData(dataSet)

        val customDescription = Description()
        customDescription.text = ""

        lineChart.apply {
            data = lineData
            setDrawGridBackground(false)
            setDrawBorders(false)
            legend.isEnabled = false
            description = customDescription
            setTouchEnabled(false)
            axisLeft.axisMinimum = 0f
            axisLeft.granularity = 1f
            isDragEnabled = true
            moveViewToX(dataSet.entryCount.toFloat())
            xAxis.setDrawAxisLine(true)
            xAxis.setDrawGridLines(true)
            xAxis.isGranularityEnabled = true
            xAxis.granularity = 1f
            xAxis.labelCount = listOfYears.size
            xAxis.textSize = 10f
            xAxis.valueFormatter = ChartValueFormatter()
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            invalidate()
        }
    }
}
