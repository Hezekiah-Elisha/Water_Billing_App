package com.olefaent.waterbilling.model

import com.squareup.moshi.Json

data class Meter(
    val id: Int,
    @Json(name = "meter_number")
    val meterNumber: String,
    @Json(name = "meter_type")
    val meterType: String,
    @Json(name = "customer_id")
    val customerId: Int,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "gps_coordinates")
    val gpsCoordinates: String,
    @Json(name = "installation_date")
    val installationDate: String
)
