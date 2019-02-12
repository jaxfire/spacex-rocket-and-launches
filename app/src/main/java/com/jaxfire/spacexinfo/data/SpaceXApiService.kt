package com.jaxfire.spacexinfo.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jaxfire.spacexinfo.data.network.response.LaunchesResponse
import com.jaxfire.spacexinfo.data.network.response.RocketsResponse
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceXApiService {

    // TODO: Move filters to request Interceptor

    @GET("rockets?filter=rocket_name,country,engines/number,active,description,rocket_id")
    fun getAllRockets(
    ): Deferred<List<RocketsResponse>>

    @GET("launches?filter=launch_year,mission_name,launch_date_utc,launch_success,links/mission_patch_small")
    fun getLaunchesForRocket(
        @Query("rocket_id") rocketId: String
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