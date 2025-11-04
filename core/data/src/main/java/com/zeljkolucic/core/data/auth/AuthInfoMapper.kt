package com.zeljkolucic.core.data.auth

import com.zeljkolucic.core.domain.AuthInfo

fun AuthInfo.toSerializableAuthInfo(): SerializableAuthInfo {
    return SerializableAuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userId = userId
    )
}

fun SerializableAuthInfo.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userId = userId
    )
}