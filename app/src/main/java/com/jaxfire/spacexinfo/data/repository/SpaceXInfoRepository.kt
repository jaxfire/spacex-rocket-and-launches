package com.jaxfire.spacexinfo.data.repository

import androidx.lifecycle.LiveData
import com.jaxfire.spacexinfo.data.network.response.LaunchResponse
import com.jaxfire.spacexinfo.data.network.response.RocketResponse


interface SpaceXInfoRepository {
    suspend fun getRocket(rocketId: String): LiveData<RocketResponse>
    suspend fun getAllRockets(): LiveData<List<RocketResponse>>
    suspend fun getLaunchesForRocket(rocketId: String): LiveData<List<LaunchResponse>>
    suspend fun refreshData()
}