package dev.maxsiomin.prodhse.feature.auth

import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.common.domain.resource.DataError
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.AuthError
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomUserData
import dev.maxsiomin.prodhse.feature.auth.domain.model.RegistrationInfo
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow

internal class FakeUsersRepository : UsersRepository {

    private var shouldReturnError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnError = value
    }

    private val users = mutableListOf<String>()

    private var activeUser: String? = null

    override suspend fun getRandomUserData(): Resource<RandomUserData, DataError> {
        if (shouldReturnError) {
            return Resource.Error(NetworkError.Server)
        }
        return Resource.Success(data = RandomUserData(
            fullName = "Random Name",
            avatarUrl = "https://randomurl.com/"
        ))
    }

    override suspend fun loginWithUsernameAndPassword(
        username: String,
        password: String
    ): Resource<Unit, AuthError.Login> {
        if (shouldReturnError) {
            return Resource.Error(AuthError.Login.Unknown(null))
        }
        activeUser = username
        return Resource.Success(Unit)
    }

    override suspend fun logout() {
        activeUser = null
    }

    override fun getAuthStatusStream(): Flow<AuthStatus> {
        TODO("Not yet implemented")
    }

    override fun getAuthStatus(): AuthStatus {
        TODO("Not yet implemented")
    }

    override suspend fun checkIfUsernameExists(username: String): Boolean {
        return username in users
    }

    override suspend fun signupWithUsernameAndPassword(info: RegistrationInfo): Resource<Unit, AuthError.Signup> {
        if (shouldReturnError) {
            return Resource.Error(AuthError.Signup.Unknown(null))
        }
        users.add(info.username)
        return Resource.Success(Unit)
    }

}

private data class User(
    val login: String,
    val password: String,
)