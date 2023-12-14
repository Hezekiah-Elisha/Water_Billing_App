package com.olefaent.waterbilling.model

import com.squareup.moshi.Json

data class Customer(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
    val location: String,
    @Json(name = "created_at")
    val createdAt: String
)
