package com.jaxfire.spacexinfo.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaxfire.spacexinfo.data.network.response.LaunchResponse
import com.jaxfire.spacexinfo.data.network.response.RocketResponse
import com.jaxfire.spacexinfo.internal.NoConnectivityException

class SpaceXInfoNetworkDataSourceImpl(
    private val apiService: SpaceXApiService
) : SpaceXInfoNetworkDataSource {

    private val _isDownloading = MutableLiveData<Boolean>()
    override val isDownloading: LiveData<Boolean>
        get() = _isDownloading

    private val _downloadedRockets = MutableLiveData<List<RocketResponse>>()
    override val downloadedRockets: LiveData<List<RocketResponse>>
        get() = _downloadedRockets

    override suspend fun fetchRockets() {
        try {
            _isDownloading.postValue(true)
            val fetchedRockets = apiService
                .getAllRockets()
                .await()
            _isDownloading.postValue(false)
            _downloadedRockets.postValue(fetchedRockets)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
            // TODO: Update the UI / notify the user. 2nd live data to display 'No internet' message?
        }
    }

    private val _downloadedLaunches = MutableLiveData<List<LaunchResponse>>()
    override val downloadedLaunches: LiveData<List<LaunchResponse>>
        get() = _downloadedLaunches

    override suspend fun fetchLaunchesForRocket(rocketId: String) {
        try {
            _isDownloading.postValue(true)
            val fetchedLaunches = apiService
                .getLaunchesForRocket(rocketId)
                .await()
            _isDownloading.postValue(false)
            _downloadedLaunches.postValue(fetchedLaunches)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
            // TODO: Update the UI / notify the user. 2nd live data to display 'No internet' message?
        }
    }
}