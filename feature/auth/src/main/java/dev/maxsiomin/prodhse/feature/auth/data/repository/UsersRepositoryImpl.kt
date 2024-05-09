package dev.maxsiomin.prodhse.feature.auth.data.repository

import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.common.data.ToDomainMapper
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.data.auth.Authenticator
import dev.maxsiomin.prodhse.feature.auth.data.dto.random_user.Result
import dev.maxsiomin.prodhse.feature.auth.data.remote.random_user.RandomUserApi
import dev.maxsiomin.prodhse.feature.auth.domain.AuthError
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomUserData
import dev.maxsiomin.prodhse.feature.auth.domain.model.RegistrationInfo
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class UsersRepositoryImpl @Inject constructor(
    private val api: RandomUserApi,
    private val authenticator: Authenticator,
    private val randomUserDataMapper: ToDomainMapper<Result, RandomUserData>,
) : UsersRepository {

    override suspend fun getRandomUserData(): Resource<RandomUserData, NetworkError> {
        val apiResponse = api.getRandomUser()
        return when (apiResponse) {
            is Resource.Error -> Resource.Error(apiResponse.error)
            is Resource.Success -> {
                apiResponse.data.results.firstOrNull()?.let(randomUserDataMapper::toDomain)?.let {
                    Resource.Success(it)
                } ?: Resource.Error(NetworkError.EmptyResponse)
            }
        }
    }

    override suspend fun loginWithUsernameAndPassword(
        username: String,
        password: String
    ): Resource<Unit, AuthError.Login> {
        return authenticator.loginWithUsernameAndPassword(username = username, password = password)
    }

    override suspend fun logout() {
        authenticator.logout()
    }

    override fun getAuthStatusFlow(): Flow<AuthStatus> {
        return authenticator.getAuthStatusFlow()
    }

    override fun getAuthStatus(): AuthStatus {
        return authenticator.getAuthStatus()
    }

    override suspend fun checkIfUsernameExists(username: String): Boolean {
        return authenticator.checkIfUsernameExists(username = username)
    }

    override suspend fun signupWithUsernameAndPassword(info: RegistrationInfo): Resource<Unit, AuthError.Signup> {
        return authenticator.registerWithUsernameAndPassword(info)
    }

}