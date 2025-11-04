package com.zeljkolucic.core.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class SerializableAuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)