package com.jaxfire.spacexinfo.data.repository

import androidx.lifecycle.LiveData
import com.jaxfire.spacexinfo.data.db.RocketDao
import com.jaxfire.spacexinfo.data.network.SpaceXInfoNetworkDataSource
import com.jaxfire.spacexinfo.data.network.response.RocketResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpaceXInfoRepositoryImpl(
    private val rocketDao: RocketDao,
    private val spaceXInfoNetworkDataSource: SpaceXInfoNetworkDataSource
) : SpaceXInfoRepository {

    init {
        // observeForever is okay in Repository as will exist for the lifetime of the application.
        spaceXInfoNetworkDataSource.downloadedRockets.observeForever { newRockets ->
            persistFetchedRockets(newRockets)
        }
    }
    override suspend fun getRockets(): LiveData<List<RocketResponse>> {
        // withContext returns a value
        return withContext(Dispatchers.IO) {
            initRocketData()
            return@withContext rocketDao.getAllRockets()
        }
    }

    private fun persistFetchedRockets(rockets: List<RocketResponse>) {
        // Global scope is okay in Repository as will exist for the lifetime of the application.
        GlobalScope.launch(Dispatchers.IO) {
            rockets.forEach { rocketDao.upsertRocket(it) } // TODO: Check if each upsert should be on a separate thread
        }
    }

    private suspend fun initRocketData() {
        if (isFetchRocketsNeeded())
            fetchRockets()
    }

    private suspend fun fetchRockets() {
        spaceXInfoNetworkDataSource.fetchRockets()
    }

    private fun isFetchRocketsNeeded(): Boolean {
        return rocketDao.getAllRockets().value!!.isEmpty() // TODO: Will live data be null if not initially set?
    }
}