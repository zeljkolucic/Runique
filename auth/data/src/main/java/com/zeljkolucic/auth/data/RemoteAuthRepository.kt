package com.zeljkolucic.auth.data

import com.zeljkolucic.auth.domain.AuthRepository
import com.zeljkolucic.core.data.networking.post
import com.zeljkolucic.core.domain.util.DataError
import com.zeljkolucic.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class RemoteAuthRepository(
    private val httpClient: HttpClient
): AuthRepository {
    override suspend fun register(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return httpClient.post(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }
}