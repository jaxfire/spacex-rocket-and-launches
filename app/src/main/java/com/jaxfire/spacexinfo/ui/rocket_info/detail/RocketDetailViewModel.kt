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

    // TODO: Review the lazy logic here
    val launches by lazyDeferred {
        spaceXInfoRepository.getLaunchesForRocket(rocketId)
    }

    val rocket by lazyDeferred {
        spaceXInfoRepository.getRocket(rocketId)
    }

    val isDownloading = spaceXInfoRepository.isDownloading()

    fun updateChart(launchData: List<LaunchEntity>, lineChart: LineChart) {

        val dataSet = LineDataSet(createEntries(launchData), "Label")

        // Chart visual customisations
        dataSet.valueFormatter = ChartValueFormatter()
        dataSet.valueTextSize = 12f
        dataSet.color = R.color.space_x_blue
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.setDrawGridBackground(false)
        lineChart.setDrawBorders(false)
        lineChart.legend.isEnabled = false
        val description = Description()
        description.text = ""
        lineChart.description = description
        lineChart.setTouchEnabled(false)
        lineChart.axisLeft.axisMinimum = 0f
        lineChart.axisLeft.granularity = 1f
        lineChart.isDragEnabled = true
        lineChart.moveViewToX(dataSet.entryCount.toFloat())
        lineChart.xAxis.setDrawAxisLine(true)
        lineChart.xAxis.setDrawGridLines(true)
        lineChart.xAxis.granularity = 1f
        lineChart.xAxis.textSize = 10f
        lineChart.xAxis.valueFormatter = ChartValueFormatter()
        lineChart.axisLeft.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.invalidate()
    }

    private fun createEntries(launchData: List<LaunchEntity>): List<Entry> {
        val yearsCount = mutableMapOf<String, Int>()
        val listOfYears = launchData.map { it.launchYear }.toMutableList()
        val listOfValidYears = listOfYears.filter { yearStr -> yearStr.toFloatOrNull() != null }
        listOfValidYears.forEach { year -> yearsCount[year] = listOfYears.count { it == year } }
        val entries = mutableListOf<Entry>()
        yearsCount.forEach { (key, value) -> entries.add(Entry(key.toFloat(), value.toFloat())) }
        return entries
    }
}
