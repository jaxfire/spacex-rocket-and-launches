package com.jaxfire.spacexinfo.data.response

import com.google.gson.annotations.SerializedName

// TODO: Remove unneeded fields - see notes

data class LaunchesResponse(
    val details: Any,
    @SerializedName("flight_number")
    val flightNumber: Int,
    @SerializedName("is_tentative")
    val isTentative: Boolean,
    @SerializedName("launch_date_local")
    val launchDateLocal: String,
    @SerializedName("launch_date_unix")
    val launchDateUnix: Int,
    @SerializedName("launch_date_utc")
    val launchDateUtc: String,
    @SerializedName("launch_failure_details")
    val launchFailureDetails: LaunchFailureDetails,
    @SerializedName("launch_site")
    val launchSite: LaunchSite,
    @SerializedName("launch_success")
    val launchSuccess: Any,
    @SerializedName("launch_window")
    val launchWindow: Any,
    @SerializedName("launch_year")
    val launchYear: String,
    val links: Links,
    @SerializedName("mission_id")
    val missionId: List<Any>,
    @SerializedName("mission_name")
    val missionName: String,
    val rocket: Rocket,
    val ships: List<Any>,
    @SerializedName("static_fire_date_unix")
    val staticFireDateUnix: Any,
    @SerializedName("static_fire_date_utc")
    val staticFireDateUtc: Any,
    val tbd: Boolean,
    val telemetry: Telemetry,
    @SerializedName("tentative_max_precision")
    val tentativeMaxPrecision: String,
    val timeline: Any,
    val upcoming: Boolean
) {
    data class Rocket(
        val fairings: Fairings,
        @SerializedName("first_stage")
        val firstStage: FirstStage,
        @SerializedName("rocket_id")
        val rocketId: String,
        @SerializedName("rocket_name")
        val rocketName: String,
        @SerializedName("rocket_type")
        val rocketType: String,
        @SerializedName("second_stage")
        val secondStage: SecondStage
    ) {
        data class FirstStage(
            val cores: List<Core>
        ) {
            data class Core(
                val block: Any,
                @SerializedName("core_serial")
                val coreSerial: Any,
                val flight: Any,
                val gridfins: Any,
                @SerializedName("land_success")
                val landSuccess: Any,
                @SerializedName("landing_intent")
                val landingIntent: Any,
                @SerializedName("landing_type")
                val landingType: Any,
                @SerializedName("landing_vehicle")
                val landingVehicle: Any,
                val legs: Any,
                val reused: Any
            )
        }

        data class SecondStage(
            val block: Any,
            val payloads: List<Payload>
        ) {
            data class Payload(
                val customers: List<String>,
                val manufacturer: String,
                val nationality: String,
                @SerializedName("norad_id")
                val noradId: List<Any>,
                val orbit: String,
                @SerializedName("orbit_params")
                val orbitParams: OrbitParams,
                @SerializedName("payload_id")
                val payloadId: String,
                @SerializedName("payload_mass_kg")
                val payloadMassKg: Any,
                @SerializedName("payload_mass_lbs")
                val payloadMassLbs: Any,
                @SerializedName("payload_type")
                val payloadType: String,
                val reused: Boolean
            ) {
                data class OrbitParams(
                    @SerializedName("apoapsis_km")
                    val apoapsisKm: Any,
                    @SerializedName("arg_of_pericenter")
                    val argOfPericenter: Any,
                    val eccentricity: Any,
                    val epoch: Any,
                    @SerializedName("inclination_deg")
                    val inclinationDeg: Any,
                    @SerializedName("lifespan_years")
                    val lifespanYears: Any,
                    val longitude: Any,
                    @SerializedName("mean_anomaly")
                    val meanAnomaly: Any,
                    @SerializedName("mean_motion")
                    val meanMotion: Any,
                    @SerializedName("periapsis_km")
                    val periapsisKm: Any,
                    @SerializedName("period_min")
                    val periodMin: Any,
                    val raan: Any,
                    @SerializedName("reference_system")
                    val referenceSystem: String,
                    val regime: String,
                    @SerializedName("semi_major_axis_km")
                    val semiMajorAxisKm: Any
                )
            }
        }

        data class Fairings(
            val recovered: Boolean,
            @SerializedName("recovery_attempt")
            val recoveryAttempt: Any,
            val reused: Boolean,
            val ship: Any
        )
    }

    data class LaunchSite(
        @SerializedName("site_id")
        val siteId: String,
        @SerializedName("site_name")
        val siteName: String,
        @SerializedName("site_name_long")
        val siteNameLong: String
    )

    data class Telemetry(
        @SerializedName("flight_club")
        val flightClub: Any
    )

    data class Links(
        @SerializedName("article_link")
        val articleLink: Any,
        @SerializedName("flickr_images")
        val flickrImages: List<Any>,
        @SerializedName("mission_patch")
        val missionPatch: Any,
        @SerializedName("mission_patch_small")
        val missionPatchSmall: Any,
        val presskit: Any,
        @SerializedName("reddit_campaign")
        val redditCampaign: Any,
        @SerializedName("reddit_launch")
        val redditLaunch: Any,
        @SerializedName("reddit_media")
        val redditMedia: Any,
        @SerializedName("reddit_recovery")
        val redditRecovery: Any,
        @SerializedName("video_link")
        val videoLink: Any,
        val wikipedia: Any,
        @SerializedName("youtube_id")
        val youtubeId: Any
    )

    data class LaunchFailureDetails(
        val altitude: Int,
        val reason: String,
        val time: Int
    )
}