package com.jaxfire.spacexinfo.data.network.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "launch_table")
data class LaunchResponse(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("flight_number")
    val flightNumber: Int,

    @SerializedName("mission_name")
    val missionName: String,

    @SerializedName("launch_date_utc")
    val launchDateUtc: String,

    @SerializedName("launch_success")
    val launchSuccess: Boolean,

    @SerializedName("launch_year")
    val launchYear: String,

    @Embedded(prefix = "links_")
    val links: Links,

    @Embedded(prefix = "rocket_")
    val rocket: Rocket
) {
    data class Links(
        @SerializedName("mission_patch_small")
        var missionPatchSmall: String = "No image available"
    )

    data class Rocket(
        @SerializedName("rocket_id")
        val rocketId: String
    )
}