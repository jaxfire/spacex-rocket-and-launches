package com.jaxfire.spacexinfo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jaxfire.spacexinfo.data.db.entity.RocketEntity


@Dao
interface RocketDao {

    @Query("SELECT * FROM rocket_table")
    fun getAllRocketsNonLive(): List<RocketEntity>

    @Query("SELECT * FROM rocket_table WHERE rocketId = :rocketId")
    fun getRocket(rocketId: String): LiveData<RocketEntity>

    @Query("SELECT * FROM rocket_table")
    fun getAllRockets(): LiveData<List<RocketEntity>>

    // TODO: Test this out when active filter is implemented
    @Query("SELECT * FROM rocket_table WHERE active = 1")
    fun getActiveRockets(): LiveData<List<RocketEntity>>

    @Insert
    fun insertRockets(rocketEntries: List<RocketEntity>)

    @Query("DELETE FROM rocket_table")
    fun deleteAll()
}