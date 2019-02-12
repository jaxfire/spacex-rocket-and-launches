package com.jaxfire.spacexinfo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jaxfire.spacexinfo.data.network.response.LaunchResponse
import com.jaxfire.spacexinfo.data.network.response.RocketResponse

@Database(
    entities = [RocketResponse::class, LaunchResponse::class],
    version = 1)
abstract class SpaceXInfoDatabase : RoomDatabase() {
    abstract fun rocketDao(): RocketDao

    companion object {
        @Volatile private var instance: SpaceXInfoDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                    SpaceXInfoDatabase::class.java, "spacex_info.db")
                    .build()
    }
}