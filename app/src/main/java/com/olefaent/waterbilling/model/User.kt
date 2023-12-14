package com.olefaent.waterbilling.model

import com.squareup.moshi.Json

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val role: String,
    @Json(name = "created_at")
    val created_at: String
)
