package com.jaxfire.spacexinfo.ui.rocket_info.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jaxfire.spacexinfo.data.repository.SpaceXInfoRepository

class RocketListViewModelFactory(
    private val spaceXInfoRepository: SpaceXInfoRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RocketListViewModel(spaceXInfoRepository) as T
    }
}