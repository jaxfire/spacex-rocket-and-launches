package com.jaxfire.spacexinfo.data.response

import com.google.gson.annotations.SerializedName

data class RocketsResponse(
    val active: Boolean,
    val country: String,
    val description: String,
    val engines: Engines,
    @SerializedName("rocket_id")
    val rocketId: String,
    @SerializedName("rocket_name")
    val rocketName: String
) {
    data class Engines(
        val number: Int
    )
}