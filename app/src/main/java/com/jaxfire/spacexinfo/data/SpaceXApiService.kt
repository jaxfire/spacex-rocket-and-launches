package com.jaxfire.spacexinfo.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jaxfire.spacexinfo.data.response.LaunchesResponse
import com.jaxfire.spacexinfo.data.response.RocketsResponse
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.spacexdata.com/v3/rockets
// https://api.spacexdata.com/v3/launches

interface SpaceXApiService {

    // TODO: Move filters to request Interceptor

    @GET("rockets")
    fun getAllRockets(
        @Query("filter") filter: String = "rocket_name,country,engines/number,active,description,rocket_id"
    ): Deferred<List<RocketsResponse>>

    @GET("launches")
    fun getLaunchesForRocket(
        @Query("rocket_id") rocketId: String,
        @Query("filter") filter: String = "launch_year,mission_name,launch_date_utc,launch_success,links/mission_patch_small"
    ): Deferred<List<LaunchesResponse>>

    companion object {
        operator fun invoke(): SpaceXApiService {

            val okHttpClient = OkHttpClient.Builder().build()

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