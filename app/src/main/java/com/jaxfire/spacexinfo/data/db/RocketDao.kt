package com.jaxfire.spacexinfo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaxfire.spacexinfo.data.network.response.LaunchResponse
import com.jaxfire.spacexinfo.data.network.response.RocketResponse


@Dao
interface RocketDao {

    @Query("SELECT * FROM rocket_table")
    fun getAllRocketsNonLive(): List<RocketResponse>

    @Query("SELECT * FROM rocket_table WHERE rocketId = :rocketId")
    fun getRocket(rocketId: String): LiveData<RocketResponse>

    @Query("SELECT * FROM rocket_table")
    fun getAllRockets(): LiveData<List<RocketResponse>>

    // TODO: Test this out when active filter is implemented
    @Query("SELECT * FROM rocket_table WHERE active = 1")
    fun getActiveRockets(): LiveData<List<RocketResponse>>

    @Insert
    fun insertRockets(rocketEntries: List<RocketResponse>)

    @Query("DELETE FROM rocket_table")
    fun deleteAll()
}