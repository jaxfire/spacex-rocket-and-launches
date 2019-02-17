package com.jaxfire.spacexinfo.ui.rocket_info.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaxfire.spacexinfo.R
import com.jaxfire.spacexinfo.internal.RocketIdNotFoundException
import com.jaxfire.spacexinfo.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.rocket_detail_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory


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
            if (it.isEmpty()) {
                rocket_detail_recyclerview.visibility = View.GONE
                rocket_detail_text_view_launches_list_no_launch_data.visibility = View.VISIBLE
                rocket_detail_text_view_launcher_per_year_no_launch_data.visibility = View.VISIBLE
            } else {
                lineChart.animate().alpha(1f).setDuration(1000).start()
                rocket_detail_recyclerview.alpha = 0f
                rocket_detail_recyclerview.visibility = View.VISIBLE
                rocket_detail_recyclerview.animate().alpha(1f).setDuration(1000).start()
                rocket_detail_text_view_launches_list_no_launch_data.visibility = View.GONE
                rocket_detail_text_view_launcher_per_year_no_launch_data.visibility = View.GONE
            }
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


