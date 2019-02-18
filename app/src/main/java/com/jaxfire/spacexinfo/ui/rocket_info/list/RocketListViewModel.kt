package com.jaxfire.spacexinfo.ui.rocket_info.list

import androidx.lifecycle.ViewModel
import com.jaxfire.spacexinfo.data.repository.SpaceXInfoRepository
import com.jaxfire.spacexinfo.internal.lazyDeferred


class RocketListViewModel(
    private val spaceXInfoRepository: SpaceXInfoRepository
) : ViewModel() {

    var filterActive = false

    val rockets by lazyDeferred {
        spaceXInfoRepository.getAllRockets()
    }

    val isDownloading = spaceXInfoRepository.isDownloading()

    fun refreshData() {
        spaceXInfoRepository.refreshData()
    }
}
