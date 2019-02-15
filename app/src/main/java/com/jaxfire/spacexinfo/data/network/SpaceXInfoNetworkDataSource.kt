package com.jaxfire.spacexinfo.data.network

import androidx.lifecycle.LiveData
import com.jaxfire.spacexinfo.data.network.response.LaunchResponse
import com.jaxfire.spacexinfo.data.network.response.RocketResponse


interface SpaceXInfoNetworkDataSource {
    val isDownloading: LiveData<Boolean>
    val downloadedRockets: LiveData<List<RocketResponse>>
    val downloadedLaunches: LiveData<List<LaunchResponse>>

    suspend fun fetchRockets()

    suspend fun fetchLaunchesForRocket(rocketId: String)
}