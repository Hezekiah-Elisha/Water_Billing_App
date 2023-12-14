package com.olefaent.waterbilling.model

import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "access_token")
    val accessToken: String,
    @Json(name = "user")
    val user: User
)