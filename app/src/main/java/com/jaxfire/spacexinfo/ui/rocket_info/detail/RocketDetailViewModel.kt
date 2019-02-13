package com.jaxfire.spacexinfo.ui.rocket_info.detail

import androidx.lifecycle.ViewModel;
import com.jaxfire.spacexinfo.data.repository.SpaceXInfoRepository
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
}
