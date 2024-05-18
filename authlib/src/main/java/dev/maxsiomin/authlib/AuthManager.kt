package dev.maxsiomin.authlib

import android.content.Context
import dev.maxsiomin.authlib.di.AppModule
import dev.maxsiomin.authlib.di.AppModuleImpl
import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.authlib.domain.model.LoginInfo
import dev.maxsiomin.authlib.domain.LoginStatus
import dev.maxsiomin.authlib.domain.model.RegistrationInfo
import dev.maxsiomin.authlib.domain.RegistrationStatus
import dev.maxsiomin.authlib.domain.model.UserInfo
import dev.maxsiomin.authlib.domain.repository.UsersRepository
import dev.maxsiomin.authlib.security.StringHasher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthManager internal constructor(
    private val repo: UsersRepository,
    private val stringHasher: StringHasher,
) {

    private val _authStatus = MutableStateFlow<AuthStatus>(AuthStatus.Loading)
    val authStatus = _authStatus.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            loadAuthStatus()
        }
    }

    suspend fun registerUser(registrationInfo: RegistrationInfo): RegistrationStatus {

        val alreadyExists = checkIfUsernameExists(registrationInfo.username)
        if (alreadyExists) {
            return RegistrationStatus.Failure("User already exists")
        }

        val hashedPassword = stringHasher.hash(registrationInfo.password)
        val userInfo = UserInfo(
            username = registrationInfo.username,
            passwordHash = hashedPassword,
            avatarUrl = registrationInfo.avatarUrl,
            fullName = registrationInfo.fullName,
        )

        repo.registerUser(userInfo)

        return RegistrationStatus.Success
    }

    suspend fun loginWithUsernameAndPassword(loginInfo: LoginInfo): LoginStatus {

        // Check if username even exists
        val userInfo = loadUserInfoByName(loginInfo.username)
            ?: return LoginStatus.Failure(INVALID_CREDENTIALS)

        // Check if password is correct
        val hashedPassword = stringHasher.hash(loginInfo.password)
        if (hashedPassword != userInfo.passwordHash) return LoginStatus.Failure(INVALID_CREDENTIALS)

        repo.login(userInfo.username)

        loadAuthStatus()

        return LoginStatus.Success
    }

    suspend fun logout() {
        repo.logout()
        loadAuthStatus()
    }

    suspend fun checkIfUsernameExists(username: String): Boolean {
        return repo.getUserByName(username) != null
    }

    private suspend fun loadAuthStatus() {
        val username = repo.getCurrentUsername()
        val result = when (username) {
            null -> AuthStatus.NotAuthenticated
            else -> {
                val userInfo = loadUserInfoByName(username = username) ?: return run {
                    _authStatus.value = AuthStatus.NotAuthenticated
                }
                AuthStatus.Authenticated(userInfo = userInfo)
            }
        }
        _authStatus.value = result
    }

    private suspend fun loadUserInfoByName(username: String): UserInfo? {
        return repo.getUserByName(username)
    }

    companion object {
        fun init(context: Context) {
            AppModuleImpl.init(context)
        }

        val instance get() = AppModule.instance.authManager

        const val INVALID_CREDENTIALS = "Incorrect username or password"
    }

}