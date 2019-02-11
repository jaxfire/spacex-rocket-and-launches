package com.jaxfire.spacexinfo.ui.rocket_info.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.jaxfire.spacexinfo.R
import kotlinx.android.synthetic.main.rocket_list_fragment.view.*

class RocketListFragment : Fragment() {

    companion object {
        fun newInstance() = RocketListFragment()
    }

    private lateinit var viewModel: RocketListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.rocket_list_fragment, container, false)

        val options = navOptions {
            anim {
                enter = R.anim.enter_from_right
                exit = R.anim.exit_to_left
                popEnter = R.anim.enter_from_left
                popExit = R.anim.exit_to_right
            }
        }

        rootView.myButton.setOnClickListener {
            findNavController().navigate(R.id.rocketDetail, null, options)
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RocketListViewModel::class.java)
        // TODO: Use the ViewModel


    }

}
