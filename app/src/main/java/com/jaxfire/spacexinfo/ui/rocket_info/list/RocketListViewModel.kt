package com.jaxfire.spacexinfo.ui.rocket_info.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaxfire.spacexinfo.data.network.response.RocketResponse
import com.jaxfire.spacexinfo.data.repository.SpaceXInfoRepository
import com.jaxfire.spacexinfo.internal.lazyDeferred
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class RocketListViewModel(
    private val spaceXInfoRepository: SpaceXInfoRepository
) : ViewModel() {

    private var filterActive = false

    val isDownloading = spaceXInfoRepository.isDownloading()

    fun refreshData() {
        spaceXInfoRepository.refreshData()
    }

    fun toggleFilter() {
        filterActive = !filterActive
    }

    suspend fun getRockets(): LiveData<List<RocketResponse>> {
        return if(filterActive) spaceXInfoRepository.getActiveRockets() else spaceXInfoRepository.getAllRockets()
    }
}
