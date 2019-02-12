package com.jaxfire.spacexinfo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jaxfire.spacexinfo.data.network.response.LaunchResponse

@Dao
interface LaunchesDao {

    @Query("SELECT * FROM launch_table WHERE rocket_rocketId = :rocketId")
    fun getLaunchesForRocketNonLive(rocketId: String): List<LaunchResponse>

    @Query("SELECT * FROM launch_table WHERE rocket_rocketId = :rocketId")
    fun getLaunchesForRocket(rocketId: String): LiveData<List<LaunchResponse>>

    @Insert
    fun insertLaunches(rocketEntries: List<LaunchResponse>)

    @Query("DELETE FROM launch_table")
    fun deleteAll()
}