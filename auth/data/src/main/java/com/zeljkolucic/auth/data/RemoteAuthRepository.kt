package com.zeljkolucic.auth.data

import com.zeljkolucic.auth.domain.AuthRepository
import com.zeljkolucic.core.data.networking.post
import com.zeljkolucic.core.domain.AuthInfo
import com.zeljkolucic.core.domain.SessionStorage
import com.zeljkolucic.core.domain.util.DataError
import com.zeljkolucic.core.domain.util.EmptyResult
import com.zeljkolucic.core.domain.util.Result
import com.zeljkolucic.core.domain.util.asEmptyResult
import io.ktor.client.HttpClient

class RemoteAuthRepository(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
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

    override suspend fun login(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password,
            )
        )
        if(result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }
        return result.asEmptyResult()
    }
}