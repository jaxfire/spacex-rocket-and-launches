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
            rocket_detail_text_view__no_launches.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            launchListAdapter.setData(it)

            viewModel.updateChart(it, lineChart)
        })

        val rocket = viewModel.rocket.await()
        rocket.observe(this@RocketDetailFragment, Observer {
            if (it == null) return@Observer
            rocket_detail_fragment_text_view_description_content.text = it.description
        })
    }
}


