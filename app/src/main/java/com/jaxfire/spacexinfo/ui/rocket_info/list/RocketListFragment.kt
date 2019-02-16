package com.jaxfire.spacexinfo.ui.rocket_info.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaxfire.spacexinfo.R
import com.jaxfire.spacexinfo.data.network.response.RocketResponse
import com.jaxfire.spacexinfo.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.rocket_list_fragment.*
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class RocketListFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: RocketListViewModelFactory by instance()
    private lateinit var viewModel: RocketListViewModel
    private lateinit var rocketListAdapter: RocketListAdapter

    private val rocketObserver:Observer<List<RocketResponse>> = Observer {
        if (it == null) return@Observer
        rocketListRecyclerView.visibility = if (it.isEmpty()) View.INVISIBLE else View.VISIBLE
        rocketListAdapter.setData(it)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.rocket_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RocketListViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {

        val linearLayoutManager = LinearLayoutManager(context)
        rocketListRecyclerView.layoutManager = linearLayoutManager
        val divider = DividerItemDecoration(rocketListRecyclerView.context, linearLayoutManager.orientation)
        rocketListRecyclerView.addItemDecoration(divider)

        rocketListAdapter = RocketListAdapter(context, listOf()) { rocketResponse ->
            navToRocketDetailScreen(rocketResponse.rocketId, rocketResponse.rocketName, rocketListRecyclerView)
        }
        rocketListRecyclerView.adapter = rocketListAdapter

        viewModel.getRockets().observe(this@RocketListFragment, rocketObserver)

        viewModel.isDownloading.observe(this@RocketListFragment, Observer {
            if(it == null) return@Observer
            swipe_container.isRefreshing = it
        })

        fab.setOnClickListener {
            switchObservable()
        }

        swipe_container.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun switchObservable() = launch {
        viewModel.getRockets().removeObservers(this@RocketListFragment)
        viewModel.toggleFilter()
        viewModel.getRockets().observe(this@RocketListFragment, rocketObserver)
    }

    private fun navToRocketDetailScreen(rocketId: String, rocketName: String, view: View) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = rocketName
        val actionDetail = RocketListFragmentDirections.actionRocketListToRocketDetail(rocketId)
        Navigation.findNavController(view).navigate(actionDetail)
    }
}
