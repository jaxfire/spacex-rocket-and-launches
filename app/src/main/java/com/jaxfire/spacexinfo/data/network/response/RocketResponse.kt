package com.jaxfire.spacexinfo.data.network.response

import com.google.gson.annotations.SerializedName

data class RocketResponse(

    @SerializedName("rocket_id")
    val rocketId: String,

    @SerializedName("rocket_name")
    val rocketName: String,

    val active: Boolean,
    val country: String,
    val description: String,

    val engines: Engines
) {
    data class Engines(
        val number: Int
    )
}