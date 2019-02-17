package com.jaxfire.spacexinfo.internal.extensions

import com.jaxfire.spacexinfo.data.db.entity.RocketEntity
import com.jaxfire.spacexinfo.data.network.response.RocketResponse


fun RocketResponse.toRocketEntity() = RocketEntity(
    rocketId = rocketId,
    rocketName = rocketName,
    active = active,
    country = country,
    description = description,
    engines = RocketEntity.Engines(engines.number)
)