package com.jaxfire.spacexinfo.data.network

import androidx.lifecycle.LiveData
import com.jaxfire.spacexinfo.data.db.entity.LaunchEntity
import com.jaxfire.spacexinfo.data.db.entity.RocketEntity
import com.jaxfire.spacexinfo.data.network.response.LaunchResponse
import com.jaxfire.spacexinfo.data.network.response.RocketResponse


interface SpaceXInfoNetworkDataSource {
    val isDownloading: LiveData<Boolean>
    val downloadedRockets: LiveData<List<RocketEntity>>
    val downloadedLaunches: LiveData<List<LaunchEntity>>

    suspend fun fetchRockets()

    suspend fun fetchLaunchesForRocket(rocketId: String)
}