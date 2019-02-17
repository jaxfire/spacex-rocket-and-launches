package com.jaxfire.spacexinfo.internal.extensions

import com.jaxfire.spacexinfo.data.db.entity.LaunchEntity
import com.jaxfire.spacexinfo.data.db.entity.RocketEntity
import com.jaxfire.spacexinfo.data.network.response.LaunchResponse
import com.jaxfire.spacexinfo.data.network.response.RocketResponse


fun LaunchResponse.toLaunchEntity() = LaunchEntity(
    flightNumber = flightNumber,
    missionName = missionName,
    launchDateUtc = launchDateUtc,
    launchSuccess = launchSuccess,
    launchYear = launchYear,
    links = LaunchEntity.Links(links.missionPatchSmall ?: "No image available"),
    rocket = LaunchEntity.Rocket(rocket.rocketId)
)