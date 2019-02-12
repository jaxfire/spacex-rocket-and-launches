package com.jaxfire.spacexinfo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.jaxfire.spacexinfo.data.db.LaunchesDao
import com.jaxfire.spacexinfo.data.db.RocketDao
import com.jaxfire.spacexinfo.data.network.SpaceXInfoNetworkDataSource
import com.jaxfire.spacexinfo.data.network.response.LaunchResponse
import com.jaxfire.spacexinfo.data.network.response.RocketResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpaceXInfoRepositoryImpl(
    private val rocketDao: RocketDao,
    private val launchesDao: LaunchesDao,
    private val spaceXInfoNetworkDataSource: SpaceXInfoNetworkDataSource
) : SpaceXInfoRepository {

    override suspend fun refreshData() {
        // TODO
//        rocketDao.deleteAll()
//        launchesDao.deleteAll()
//        getRockets()
    }

    init {
        // observeForever is okay in Repository as will exist for the lifetime of the application.
        spaceXInfoNetworkDataSource.apply {
            downloadedRockets.observeForever { newRockets ->
                persistFetchedRockets(newRockets)
            }
            downloadedLaunches.observeForever { newLaunches ->
                persistFetchedLaunches(newLaunches)
            }
        }
    }

    override suspend fun getRockets(): LiveData<List<RocketResponse>> {
        // withContext returns a value
        return withContext(Dispatchers.IO) {
            initRocketData()
            return@withContext rocketDao.getAllRockets()
            // TODO: Active rocket filter
            // return@withContext if (activeRocketsFilter) rocketDao.getAllRockets() else rocketDao.getActiveRockets()
        }
    }

    override suspend fun getLaunchesForRocket(rocketId: String): LiveData<List<LaunchResponse>> {
        return withContext(Dispatchers.IO) {
            initLaunchData(rocketId)
            return@withContext launchesDao.getLaunchesForRocket(rocketId)
        }
    }

    private fun persistFetchedRockets(rockets: List<RocketResponse>) {
        // Global scope is okay in Repository as will exist for the lifetime of the application.
        GlobalScope.launch(Dispatchers.IO) {

            // TODO: Convert response objects to db entities here?
            rocketDao.deleteAll()
            rocketDao.insertRockets(rockets)
        }
    }

    private fun persistFetchedLaunches(launches: List<LaunchResponse>) {
        // Global scope is okay in Repository as will exist for the lifetime of the application.
        GlobalScope.launch(Dispatchers.IO) {

            // TODO: Convert response objects to db entities here?
            launches.forEach {
                if (it.links.missionPatchSmall == null) it.links.missionPatchSmall = "No image available"
            }
            launchesDao.insertLaunches(launches)
        }
    }

    private suspend fun initRocketData() {
        if (isFetchRocketsNeeded())
            fetchRockets()
    }

    private suspend fun initLaunchData(rocketId: String) {
        if (isFetchLaunchesNeeded(rocketId))
            fetchLaunches(rocketId)
    }

    private suspend fun fetchRockets() {
        spaceXInfoNetworkDataSource.fetchRockets()
    }

    private suspend fun fetchLaunches(rocketId: String) {
        spaceXInfoNetworkDataSource.fetchLaunchesForRocket(rocketId)
    }

    private fun isFetchRocketsNeeded(): Boolean {
        return rocketDao.getAllRocketsNonLive().isEmpty()
    }

    private fun isFetchLaunchesNeeded(rocketId: String): Boolean {
        return launchesDao.getLaunchesForRocketNonLive(rocketId).isEmpty()
    }
}