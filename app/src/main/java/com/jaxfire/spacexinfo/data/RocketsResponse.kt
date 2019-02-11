package com.jaxfire.spacexinfo.data

import com.google.gson.annotations.SerializedName

// TODO: Remove unneeded fields - see notes

data class RocketsResponse(
    val active: Boolean,
    val boosters: Int,
    val company: String,
    @SerializedName("cost_per_launch")
    val costPerLaunch: Int,
    val country: String,
    val description: String,
    val diameter: Diameter,
    val engines: Engines,
    @SerializedName("first_flight")
    val firstFlight: String,
    @SerializedName("first_stage")
    val firstStage: FirstStage,
    @SerializedName("flickr_images")
    val flickrImages: List<String>,
    val height: Height,
    val id: Int,
    @SerializedName("landing_legs")
    val landingLegs: LandingLegs,
    val mass: Mass,
    @SerializedName("payload_weights")
    val payloadWeights: List<PayloadWeight>,
    @SerializedName("rocket_id")
    val rocketId: String,
    @SerializedName("rocket_name")
    val rocketName: String,
    @SerializedName("rocket_type")
    val rocketType: String,
    @SerializedName("second_stage")
    val secondStage: SecondStage,
    val stages: Int,
    @SerializedName("success_rate_pct")
    val successRatePct: Int,
    val wikipedia: String
) {
    data class SecondStage(
        @SerializedName("burn_time_sec")
        val burnTimeSec: Any,
        val engines: Int,
        @SerializedName("fuel_amount_tons")
        val fuelAmountTons: Int,
        val payloads: Payloads,
        val reusable: Boolean,
        val thrust: Thrust
    ) {
        data class Thrust(
            val kN: Int,
            val lbf: Int
        )

        data class Payloads(
            @SerializedName("composite_fairing")
            val compositeFairing: CompositeFairing,
            @SerializedName("option_1")
            val option1: String,
            @SerializedName("option_2")
            val option2: String
        ) {
            data class CompositeFairing(
                val diameter: Diameter,
                val height: Height
            ) {
                data class Diameter(
                    val feet: Any,
                    val meters: Any
                )

                data class Height(
                    val feet: Any,
                    val meters: Any
                )
            }
        }
    }

    data class FirstStage(
        @SerializedName("burn_time_sec")
        val burnTimeSec: Any,
        val engines: Int,
        @SerializedName("fuel_amount_tons")
        val fuelAmountTons: Int,
        val reusable: Boolean,
        @SerializedName("thrust_sea_level")
        val thrustSeaLevel: ThrustSeaLevel,
        @SerializedName("thrust_vacuum")
        val thrustVacuum: ThrustVacuum
    ) {
        data class ThrustSeaLevel(
            val kN: Int,
            val lbf: Int
        )

        data class ThrustVacuum(
            val kN: Int,
            val lbf: Int
        )
    }

    data class Engines(
        @SerializedName("engine_loss_max")
        val engineLossMax: Any,
        val layout: Any,
        val number: Int,
        @SerializedName("propellant_1")
        val propellant1: String,
        @SerializedName("propellant_2")
        val propellant2: String,
        @SerializedName("thrust_sea_level")
        val thrustSeaLevel: ThrustSeaLevel,
        @SerializedName("thrust_to_weight")
        val thrustToWeight: Any,
        @SerializedName("thrust_vacuum")
        val thrustVacuum: ThrustVacuum,
        val type: String,
        val version: String
    ) {
        data class ThrustSeaLevel(
            val kN: Int,
            val lbf: Int
        )

        data class ThrustVacuum(
            val kN: Int,
            val lbf: Int
        )
    }

    data class PayloadWeight(
        val id: String,
        val kg: Int,
        val lb: Int,
        val name: String
    )

    data class Height(
        val feet: Int,
        val meters: Int
    )

    data class Mass(
        val kg: Int,
        val lb: Int
    )

    data class LandingLegs(
        val material: String,
        val number: Int
    )

    data class Diameter(
        val feet: Int,
        val meters: Int
    )
}