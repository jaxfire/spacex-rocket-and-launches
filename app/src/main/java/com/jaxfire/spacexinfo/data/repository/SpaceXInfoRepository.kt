package com.jaxfire.spacexinfo.data.repository

import androidx.lifecycle.LiveData
import com.jaxfire.spacexinfo.data.db.entity.LaunchEntity
import com.jaxfire.spacexinfo.data.db.entity.RocketEntity


interface SpaceXInfoRepository {
    fun isDownloading(): LiveData<Boolean>
    suspend fun getRocket(rocketId: String): LiveData<RocketEntity>
    suspend fun getAllRockets(): LiveData<List<RocketEntity>>
    suspend fun getLaunchesForRocket(rocketId: String): LiveData<List<LaunchEntity>>
    fun refreshData()
}