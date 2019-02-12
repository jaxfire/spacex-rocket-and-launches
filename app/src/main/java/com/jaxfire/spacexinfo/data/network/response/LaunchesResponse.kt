package com.jaxfire.spacexinfo.data.network.response

import com.google.gson.annotations.SerializedName

data class LaunchesResponse(
    @SerializedName("launch_date_utc")
    val launchDateUtc: String,
    @SerializedName("launch_success")
    val launchSuccess: Any,
    @SerializedName("launch_year")
    val launchYear: String,
    val links: Links,
    @SerializedName("mission_name")
    val missionName: String
) {
    data class Links(
        @SerializedName("mission_patch_small")
        val missionPatchSmall: Any
    )
}