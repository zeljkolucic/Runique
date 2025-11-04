package com.zeljkolucic.core.data.networking

data class AccessTokenRequest(
    val refreshToken: String,
    val userId: String
)