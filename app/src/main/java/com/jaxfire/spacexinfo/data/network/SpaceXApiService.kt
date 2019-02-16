package com.jaxfire.spacexinfo.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jaxfire.spacexinfo.data.network.response.LaunchResponse
import com.jaxfire.spacexinfo.data.network.response.RocketResponse
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val ROCKET_JSON_MASK_FILTER = "rocket_name,country,engines/number,active,description,rocket_id"
const val LAUNCH_JSON_MASK__FILTER = "flight_number,rocket/rocket_id,launch_year,mission_name,launch_date_utc,launch_success,links/mission_patch_small"

interface SpaceXApiService {

    @GET("rockets?filter=$ROCKET_JSON_MASK_FILTER")
    fun getAllRockets(
    ): Deferred<List<RocketResponse>>

    @GET("launches?filter=$LAUNCH_JSON_MASK__FILTER")
    fun getLaunchesForRocket(
        @Query("rocket_id") rocketId: String
    ): Deferred<List<LaunchResponse>>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): SpaceXApiService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.spacexdata.com/v3/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SpaceXApiService::class.java)
        }
    }
}