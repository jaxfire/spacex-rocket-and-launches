package com.jaxfire.spacexinfo.ui.rocket_info.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jaxfire.spacexinfo.internal.RocketIdNotFoundException
import com.jaxfire.spacexinfo.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.rocket_detail_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import com.jaxfire.spacexinfo.R
import kotlinx.android.synthetic.main.rocket_list_fragment.*
import java.text.DecimalFormat


class RocketDetailFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    // Factory which creates a viewModelFactory
    private val viewModelFactoryInstanceFactory
            : ((String) -> RocketDetailViewModelFactory) by factory()

    private lateinit var viewModel: RocketDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.rocket_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { RocketDetailFragmentArgs.fromBundle(it) }
        val rocketId = safeArgs?.rocketId ?: throw RocketIdNotFoundException()
        viewModel = ViewModelProviders.of(this, viewModelFactoryInstanceFactory(rocketId))
            .get(RocketDetailViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {

        val linearLayoutManager = LinearLayoutManager(context)
        rocket_detail_recyclerview.layoutManager = linearLayoutManager
        val launchListAdapter = LaunchListAdapter(context, emptyList())
        rocket_detail_recyclerview.adapter = launchListAdapter
        val divider = DividerItemDecoration(rocket_detail_recyclerview.context, linearLayoutManager.orientation)
        rocket_detail_recyclerview.addItemDecoration(divider)

        val launches = viewModel.launches.await()
        launches.observe(this@RocketDetailFragment, Observer {

            if (it == null) return@Observer
            rocket_detail_recyclerview.visibility = if (it.isEmpty()) View.INVISIBLE else View.VISIBLE
            rocket_detail_text_view__no_launches.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
            launchListAdapter.setData(it)

            rocket_detail_text_view__no_launches.visibility = View.GONE

            val yearsCount = mutableMapOf<String, Int>()

            val listOfYears = it.map { it.launchYear }.toMutableList()

            val listOfValidYears = listOfYears.filter { yearStr ->
                yearStr.toFloatOrNull() != null
            }

            listOfValidYears.forEach { year ->
                yearsCount[year] = listOfYears.count { it == year }
            }

            val entries = mutableListOf<Entry>()
            yearsCount.forEach { (key, value) ->
                entries.add(Entry(key.toFloat(), value.toFloat()))
            }

            // TODO: Remove logging
            entries.forEach {
                Log.d("jim", "x: ${it.x} y: ${it.y}")
            }

            val dataSet = LineDataSet(entries, "Label")
            dataSet.valueFormatter = ValueFormatter()
            dataSet.valueTextSize = 12f
            dataSet.color = R.color.space_x_blue
//            dataSet.valueTextColor = R.color.pr

            val lineData = LineData(dataSet)
            line_chart.data = lineData
            line_chart.setDrawGridBackground(false)
            line_chart.setDrawBorders(false)
            line_chart.legend.isEnabled = false
            val description = Description()
            description.text = ""
            line_chart.description = description
            line_chart.setTouchEnabled(false)
//            line_chart.getAxis(YAxis.AxisDependency)
            line_chart.axisLeft.axisMinimum = 0f
            line_chart.axisLeft.granularity = 1f
//            line_chart.axisRight.axisMinimum = 2000f

//            line_chart.setVisibleXRangeMaximum(6f)

            line_chart.isDragEnabled = true

            line_chart.moveViewToX(dataSet.entryCount.toFloat())

            line_chart.xAxis.setDrawAxisLine(true)
            line_chart.xAxis.setDrawGridLines(true)
//            line_chart.offsetTopAndBottom(20)


            line_chart.xAxis.granularity = 1f
            line_chart.xAxis.textSize = 10f
            line_chart.xAxis.valueFormatter = ValueFormatter()

            line_chart.axisLeft.isEnabled = false
            line_chart.axisRight.isEnabled = false
//            leftAxis.setDrawAxisLine(false)
//            leftAxis.setDrawZeroLine(true)

//            rightAxis.setDrawAxisLine(false)


            line_chart.invalidate()
        })

        val rocket = viewModel.rocket.await()
        rocket.observe(this@RocketDetailFragment, Observer {
            if (it == null) return@Observer
            rocket_detail_fragment_text_view_description_content.text = it.description
        })
    }
}

class ValueFormatter
    () : IAxisValueFormatter, IValueFormatter {

    // format values to 1 decimal digit
    private val mFormat: DecimalFormat = DecimalFormat("#")

    /** this is only needed if numbers are returned, else return 0  */
    val decimalDigits: Int
        get() = 1

    override fun getFormattedValue(value: Float, axis: AxisBase): String {
        // "value" represents the position of the label on the axis (x or y)
        return mFormat.format(value)
    }

    override fun getFormattedValue(value: Float, entry: Entry?, dataSetIndex: Int, viewPortHandler: ViewPortHandler?
    ): String {
        return mFormat.format(value)
    }
}
