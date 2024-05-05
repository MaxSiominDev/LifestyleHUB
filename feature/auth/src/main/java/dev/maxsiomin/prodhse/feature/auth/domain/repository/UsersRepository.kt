package dev.maxsiomin.prodhse.feature.auth.domain.repository

import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.AuthError
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomUserData
import dev.maxsiomin.prodhse.feature.auth.domain.model.RegistrationInfo
import kotlinx.coroutines.flow.Flow

internal interface UsersRepository {

    suspend fun getRandomUserData(): Resource<RandomUserData, NetworkError>

    suspend fun loginWithUsernameAndPassword(
        username: String,
        password: String
    ): Resource<Unit, AuthError.Login>

    suspend fun logout()

    suspend fun getAuthStatusFlow(): Flow<AuthStatus>

    fun getAuthStatus(): AuthStatus

    suspend fun checkIfUsernameExists(username: String): Boolean

    suspend fun signupWithUsernameAndPassword(info: RegistrationInfo): Resource<Unit, AuthError.Signup>

}

