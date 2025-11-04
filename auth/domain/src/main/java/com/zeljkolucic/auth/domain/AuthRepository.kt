package com.zeljkolucic.auth.domain

import com.zeljkolucic.core.domain.util.DataError
import com.zeljkolucic.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
}