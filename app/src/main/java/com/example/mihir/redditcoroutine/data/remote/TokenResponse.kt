package com.example.mihir.redditcoroutine.data.remote

import com.squareup.moshi.Json

data class TokenResponse(
        @field:Json(name = "access_token") val accessToken: String,
        @field:Json(name = "expires_in") val expiresIn: Int,
        @field:Json(name = "token_type") val tokenType: String,
        @field:Json(name = "scope") val scope: String,
        @field:Json(name = "refresh_token") val refreshToken: String,
        @field:Json(name = "device_id") val deviceId: String
)