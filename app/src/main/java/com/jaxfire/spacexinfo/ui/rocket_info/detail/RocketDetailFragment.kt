package com.jaxfire.spacexinfo.ui.rocket_info.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Rocket Name Here"

        bindUI()
    }

    private fun bindUI() = launch {
        val rockets = viewModel.launches.await()
        rockets.observe(this@RocketDetailFragment, Observer {
            if (it == null || it.isEmpty()) return@Observer
            textViewDetail.text = it[0].toString()
        })
    }
}
