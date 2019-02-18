package com.jaxfire.spacexinfo.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaxfire.spacexinfo.data.db.entity.LaunchEntity
import com.jaxfire.spacexinfo.data.db.entity.RocketEntity
import com.jaxfire.spacexinfo.internal.NoConnectivityException
import com.jaxfire.spacexinfo.internal.extensions.toLaunchEntity
import com.jaxfire.spacexinfo.internal.extensions.toRocketEntity

class SpaceXInfoNetworkDataSourceImpl(
    private val apiService: SpaceXApiService
) : SpaceXInfoNetworkDataSource {

    private val _isDownloading = MutableLiveData<Boolean>()
    override val isDownloading: LiveData<Boolean>
        get() = _isDownloading

    private val _downloadedRockets = MutableLiveData<List<RocketEntity>>()
    override val downloadedRockets: LiveData<List<RocketEntity>>
        get() = _downloadedRockets

    override suspend fun fetchRockets() {
        try {
            _isDownloading.postValue(true)
            val fetchedRockets = apiService
                .getAllRockets()
                .await()
            _isDownloading.postValue(false)

            _downloadedRockets.postValue(fetchedRockets.map { it.toRocketEntity() })
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
            // TODO: Update the UI / notify the user. 2nd live data to display 'No internet' message
            _downloadedRockets.postValue(emptyList())
        }
    }

    private val _downloadedLaunches = MutableLiveData<List<LaunchEntity>>()
    override val downloadedLaunches: LiveData<List<LaunchEntity>>
        get() = _downloadedLaunches

    override suspend fun fetchLaunchesForRocket(rocketId: String) {
        try {
            _isDownloading.postValue(true)
            val fetchedLaunches = apiService
                .getLaunchesForRocket(rocketId)
                .await()
            _isDownloading.postValue(false)
            _downloadedLaunches.postValue(fetchedLaunches.map { it.toLaunchEntity() })
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
            // TODO: Update the UI / notify the user. 2nd live data to display 'No internet' message?
            _downloadedRockets.postValue(emptyList())
        }
    }
}