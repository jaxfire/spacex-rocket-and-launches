package com.jaxfire.spacexinfo.data.repository

import androidx.lifecycle.LiveData
import com.jaxfire.spacexinfo.data.db.LaunchesDao
import com.jaxfire.spacexinfo.data.db.RocketDao
import com.jaxfire.spacexinfo.data.db.entity.LaunchEntity
import com.jaxfire.spacexinfo.data.db.entity.RocketEntity
import com.jaxfire.spacexinfo.data.network.SpaceXInfoNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpaceXInfoRepositoryImpl(
    private val rocketDao: RocketDao,
    private val launchesDao: LaunchesDao,
    private val spaceXInfoNetworkDataSource: SpaceXInfoNetworkDataSource
) : SpaceXInfoRepository {


    private val isDownloading = spaceXInfoNetworkDataSource.isDownloading

    override fun isDownloading(): LiveData<Boolean> = isDownloading

    override fun refreshData() {
        GlobalScope.launch(Dispatchers.IO) {
            rocketDao.deleteAll()
            launchesDao.deleteAll()
            spaceXInfoNetworkDataSource.fetchRockets()
        }
    }

    init {
        // observeForever is okay in repository as will exist for the lifetime of the application.
        spaceXInfoNetworkDataSource.apply {
            downloadedRockets.observeForever { newRockets ->
                persistFetchedRockets(newRockets)
            }
            downloadedLaunches.observeForever { newLaunches ->
                persistFetchedLaunches(newLaunches)
            }
        }
    }

    override suspend fun getAllRockets(): LiveData<List<RocketEntity>> {
        // withContext returns a value
        return withContext(Dispatchers.IO) {
            initRocketData()
            return@withContext rocketDao.getAllRockets()
        }
    }

    override suspend fun getRocket(rocketId: String): LiveData<RocketEntity> {
        // withContext returns a value
        return withContext(Dispatchers.IO) {
            return@withContext rocketDao.getRocket(rocketId)
        }
    }

    override suspend fun getLaunchesForRocket(rocketId: String): LiveData<List<LaunchEntity>> {
        return withContext(Dispatchers.IO) {
            initLaunchData(rocketId)
            return@withContext launchesDao.getLaunchesForRocket(rocketId)
        }
    }

    private fun persistFetchedRockets(rockets: List<RocketEntity>) {
        // Global scope is okay in Repository as will exist for the lifetime of the application.
        GlobalScope.launch(Dispatchers.IO) {

            rocketDao.deleteAll()
            rocketDao.insertRockets(rockets)
        }
    }

    private fun persistFetchedLaunches(launches: List<LaunchEntity>) {
        // Global scope is okay in Repository as will exist for the lifetime of the application.
        GlobalScope.launch(Dispatchers.IO) {
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