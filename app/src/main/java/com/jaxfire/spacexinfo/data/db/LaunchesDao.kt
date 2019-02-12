package com.jaxfire.spacexinfo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaxfire.spacexinfo.data.network.response.LaunchResponse

@Dao
interface LaunchesDao {

    @Query("SELECT * FROM launch_table WHERE rocket_rocketId = :rocketId")
    fun getLaunchesForRocketNonLive(rocketId: String): List<LaunchResponse>

    @Query("SELECT * FROM launch_table WHERE rocket_rocketId = :rocketId")
    fun getLaunchesForRocket(rocketId: String): LiveData<List<LaunchResponse>>

    // The API provides two launches with the same flight_number (89). I will overwrite the original with the duplicate.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLaunches(rocketEntries: List<LaunchResponse>)

    @Query("DELETE FROM launch_table")
    fun deleteAll()
}