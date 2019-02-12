package com.jaxfire.spacexinfo.ui.rocket_info.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jaxfire.spacexinfo.data.repository.SpaceXInfoRepository

class RocketDetailViewModelFactory(
    private val spaceXInfoRepository: SpaceXInfoRepository,
    private val rocketId: String
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RocketDetailViewModel(spaceXInfoRepository, rocketId) as T
    }
}