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
    fun getAllRockets(): LiveData<List<RocketResponse>>

//    @Query("SELECT * FROM launch WHERE rocket_rocketId = :rocketId")
//    fun getLaunches(rocketId: String)

    // TODO: Do we need upsert if we are going to delete all data anyway?

    // TODO: Before release. Check if still have no foreign keys in our tables.
    // REPLACE will work fine as we have no foreign keys in our tables.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertRocket(rocketEntry: RocketResponse) // TODO: Refactor if we create db entities separate from network model

//    @Insert(onConflict = OnConflictStrategy.REPLACE) // TODO: Refactor if we create db entities separate from network model
//    fun upsertLaunch(launchEntry: LaunchResponse)

    @Query("DELETE FROM rocket_table")
    fun deleteAll()
}