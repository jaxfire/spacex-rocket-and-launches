package com.jaxfire.spacexinfo.ui.rocket_info.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaxfire.spacexinfo.R
import com.jaxfire.spacexinfo.data.db.entity.RocketEntity
import com.jaxfire.spacexinfo.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.rocket_list_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class RocketListFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: RocketListViewModelFactory by instance()

    private lateinit var viewModel: RocketListViewModel

    private lateinit var rocketListAdapter: RocketListAdapter

    private lateinit var latestRocketData: List<RocketEntity>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.rocket_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RocketListViewModel::class.java)

        bindUI()

        handleWelcomeDialog()
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

        val rockets = viewModel.rockets.await()
        rockets.observe(this@RocketListFragment, Observer {
            if (it == null) return@Observer
            rocketListRecyclerView.visibility = if (it.isEmpty()) View.INVISIBLE else View.VISIBLE
            latestRocketData = it
            rocketListAdapter.setData(latestRocketData.filter { if (viewModel.filterActive) it.active else true })
        })

        viewModel.isDownloading.observe(this@RocketListFragment, Observer {
            if(it == null) return@Observer
            swipe_container.isRefreshing = it
        })

        fab.setOnClickListener { view ->
            viewModel.filterActive = !viewModel.filterActive
            rocketListAdapter.setData(latestRocketData.filter { if (viewModel.filterActive) it.active else true })
        }

        swipe_container.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun navToRocketDetailScreen(rocketId: String, rocketName: String, view: View) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = rocketName
        val actionDetail = RocketListFragmentDirections.actionRocketListToRocketDetail(rocketId)
        Navigation.findNavController(view).navigate(actionDetail)
    }

    private fun handleWelcomeDialog() {
        val sharedPrefs = activity?.getSharedPreferences("application_prefs", Context.MODE_PRIVATE) ?: return
        if (sharedPrefs.getBoolean("first_run", true)) {
            with(sharedPrefs.edit()) {
                putBoolean("first_run", false)
                apply()
            }
            showWelcomeDialog()
        }
    }

    private fun showWelcomeDialog() {

        val builder = AlertDialog.Builder(this.context!!)
        builder.setTitle("Welcome to SpaceXInfo")
        builder.setMessage("You can Swipe-To-Refresh to get the latest information and you can filter only active rockets using the FAB.")
        builder.setCancelable(true)
        builder.setPositiveButton("Dismiss") { dialog, _ -> dialog.cancel() }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}
