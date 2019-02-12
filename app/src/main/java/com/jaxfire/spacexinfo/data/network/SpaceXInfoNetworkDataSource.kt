package com.jaxfire.spacexinfo.data.network

import androidx.lifecycle.LiveData
import com.jaxfire.spacexinfo.data.network.response.RocketResponse


interface SpaceXInfoNetworkDataSource {
    val downloadedRockets: LiveData<List<RocketResponse>>

    suspend fun fetchRockets()
}