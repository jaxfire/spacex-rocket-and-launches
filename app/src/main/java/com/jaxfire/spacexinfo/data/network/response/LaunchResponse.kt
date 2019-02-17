package com.jaxfire.spacexinfo.data.network.response

import com.google.gson.annotations.SerializedName

data class LaunchResponse(

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

    val links: Links,

    val rocket: Rocket
) {
    data class Links(
        @SerializedName("mission_patch_small")
        var missionPatchSmall: String
    )
    data class Rocket(
        @SerializedName("rocket_id")
        val rocketId: String
    )
}