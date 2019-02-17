package com.jaxfire.spacexinfo.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launch_table")
data class LaunchEntity(

    @PrimaryKey(autoGenerate = false)
    val flightNumber: Int,

    val missionName: String,

    val launchDateUtc: String,

    val launchSuccess: Boolean,

    val launchYear: String,

    @Embedded(prefix = "links_")
    val links: Links,

    @Embedded(prefix = "rocket_")
    val rocket: Rocket
) {
    data class Links(
        var missionPatchSmall: String
    )
    data class Rocket(
        val rocketId: String
    )
}