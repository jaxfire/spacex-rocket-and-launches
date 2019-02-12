package com.jaxfire.spacexinfo.ui.rocket_info.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.jaxfire.spacexinfo.R
import com.jaxfire.spacexinfo.data.network.SpaceXApiService
import com.jaxfire.spacexinfo.data.network.ConnectivityInterceptorImpl
import com.jaxfire.spacexinfo.data.network.SpaceXInfoNetworkDataSourceImpl
import com.jaxfire.spacexinfo.ui.ToolbarTitleListener
import kotlinx.android.synthetic.main.rocket_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RocketDetailFragment : Fragment() {

    companion object {
        fun newInstance() = RocketDetailFragment()
    }

    private lateinit var viewModel: RocketDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.rocket_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RocketDetailViewModel::class.java)

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Rocket Name Here"
    }
}
