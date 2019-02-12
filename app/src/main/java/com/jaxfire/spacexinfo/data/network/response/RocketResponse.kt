package com.jaxfire.spacexinfo.data.network.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "rocket_table")
data class RocketResponse(

    @PrimaryKey
    @SerializedName("rocket_id")
    val rocketId: String,

    @SerializedName("rocket_name")
    val rocketName: String,

    val active: Boolean,
    val country: String,
    val description: String,

    @Embedded(prefix = "engines_")
    val engines: Engines
) {
    data class Engines(
        val number: Int
    )
}