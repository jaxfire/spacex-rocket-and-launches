package com.jaxfire.spacexinfo.ui.rocket_info.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaxfire.spacexinfo.R
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
        val rockets = viewModel.rockets.await()
        rockets.observe(this@RocketListFragment, Observer {
            if (it == null || it.isEmpty()) return@Observer
            rocketListRecyclerView.layoutManager = LinearLayoutManager(context)
            rocketListRecyclerView.adapter =
                RocketListAdapter(context, it) { rocketResponse ->
                    navToRocketDetailScreen(rocketResponse.rocketId, rocketResponse.rocketName, rocketListRecyclerView)
                }
        })
    }

    private fun navToRocketDetailScreen(rocketId: String, rocketName: String, view: View) {
        val actionDetail = RocketListFragmentDirections.actionRocketListToRocketDetail(rocketId, rocketName)
        val options = navOptions {
            anim {
                enter = R.anim.enter_from_right
                exit = R.anim.exit_to_left
                popEnter = R.anim.enter_from_left
                popExit = R.anim.exit_to_right
            }
        }
        Navigation.findNavController(view).navigate(actionDetail, options)
    }
}
