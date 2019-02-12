package com.jaxfire.spacexinfo.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaxfire.spacexinfo.data.network.response.RocketResponse
import com.jaxfire.spacexinfo.internal.NoConnectivityException

class SpaceXInfoNetworkDataSourceImpl(
    private val apiService: SpaceXApiService
) : SpaceXInfoNetworkDataSource {

    private val _downloadedRockets = MutableLiveData<List<RocketResponse>>()

    override val downloadedRockets: LiveData<List<RocketResponse>>
        get() = _downloadedRockets

    override suspend fun fetchRockets() {
        try {
            val fetchedRockets = apiService
                .getAllRockets()
                .await()
            _downloadedRockets.postValue(fetchedRockets)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e) // TODO: Update the UI / notify the user
        }
    }
}