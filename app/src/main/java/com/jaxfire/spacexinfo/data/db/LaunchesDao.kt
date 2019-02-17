package com.jaxfire.spacexinfo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaxfire.spacexinfo.data.db.entity.LaunchEntity

@Dao
interface LaunchesDao {

    @Query("SELECT * FROM launch_table WHERE rocket_rocketId = :rocketId")
    fun getLaunchesForRocketNonLive(rocketId: String): List<LaunchEntity>

    @Query("SELECT * FROM launch_table WHERE rocket_rocketId = :rocketId")
    fun getLaunchesForRocket(rocketId: String): LiveData<List<LaunchEntity>>

    // The API provides two launches with the same flight_number (89). I will overwrite the original with the duplicate.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLaunches(rocketEntries: List<LaunchEntity>)

    @Query("DELETE FROM launch_table")
    fun deleteAll()
}