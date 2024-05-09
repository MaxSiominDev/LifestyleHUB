package dev.maxsiomin.prodhse.feature.auth.data.auth

import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.authlib.domain.LoginStatus
import dev.maxsiomin.authlib.domain.RegistrationStatus
import dev.maxsiomin.authlib.domain.model.LoginInfo
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.AuthError
import dev.maxsiomin.prodhse.feature.auth.domain.model.RegistrationInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal interface Authenticator {

    suspend fun loginWithUsernameAndPassword(
        username: String,
        password: String
    ): Resource<Unit, AuthError.Login>

    suspend fun logout()

    fun getAuthStatusFlow(): Flow<AuthStatus>

    fun getAuthStatus(): AuthStatus

    suspend fun checkIfUsernameExists(username: String): Boolean

    suspend fun registerWithUsernameAndPassword(info: RegistrationInfo): Resource<Unit, AuthError.Signup>

}

internal class AuthenticatorImpl @Inject constructor(
    private val authManager: AuthManager
) : Authenticator {

    override suspend fun loginWithUsernameAndPassword(
        username: String,
        password: String
    ): Resource<Unit, AuthError.Login> {
        val loginInfo = LoginInfo(username = username, password = password)
        val loginStatus = authManager.loginWithUsernameAndPassword(loginInfo)
        return when (loginStatus) {

            is LoginStatus.Failure -> {
                Resource.Error(
                    AuthError.Login.Unknown(reason = loginStatus.reason)
                )
            }

            LoginStatus.Success -> {
                Resource.Success(Unit)
            }

        }
    }

    override suspend fun logout() {
        authManager.logout()
    }

    override fun getAuthStatusFlow(): Flow<AuthStatus> {
        return authManager.authStatus
    }

    override fun getAuthStatus(): AuthStatus {
        return authManager.authStatus.value
    }

    override suspend fun checkIfUsernameExists(username: String): Boolean {
        return authManager.checkIfUsernameExists(username = username)
    }

    override suspend fun registerWithUsernameAndPassword(info: RegistrationInfo): Resource<Unit, AuthError.Signup> {
        val registrationInfo = dev.maxsiomin.authlib.domain.model.RegistrationInfo(
            username = info.username,
            password = info.password,
            avatarUrl = info.avatarUrl,
            fullName = info.fullName,
        )
        val registrationStatus = authManager.registerUser(registrationInfo)

        when (registrationStatus) {
            is RegistrationStatus.Failure -> {
                val error = AuthError.Signup.Unknown(registrationStatus.reason)
                return Resource.Error(error)
            }
            RegistrationStatus.Success -> return Resource.Success(Unit)
        }
    }

}
