package com.jaxfire.spacexinfo.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rocket_table")
data class RocketEntity(

    @PrimaryKey
    val rocketId: String,

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