package dev.maxsiomin.authlib

import android.content.Context
import dev.maxsiomin.authlib.data.mappers.UserEntityToUiModelMapper
import dev.maxsiomin.authlib.di.AppModule
import dev.maxsiomin.authlib.di.AppModuleImpl
import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.authlib.domain.LoginInfo
import dev.maxsiomin.authlib.domain.LoginStatus
import dev.maxsiomin.authlib.domain.RegistrationInfo
import dev.maxsiomin.authlib.domain.RegistrationStatus
import dev.maxsiomin.authlib.domain.UserInfo
import dev.maxsiomin.authlib.domain.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthManager internal constructor(
    private val repo: UsersRepository,
) {

    private val _authStatus = MutableStateFlow<AuthStatus>(AuthStatus.Loading)
    val authStatus = _authStatus.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            loadAuthStatus()
        }
    }

    suspend fun registerUser(registrationInfo: RegistrationInfo): RegistrationStatus {
        TODO()
    }

    suspend fun loginWithUsernameAndPassword(loginInfo: LoginInfo): LoginStatus {
        TODO()
    }

    suspend fun checkIfUsernameExists(username: String): Boolean {
        return repo.getUserByName(username) != null
    }

    private suspend fun loadAuthStatus(): AuthStatus {
        val username = repo.getCurrentUsername()
        return when (username) {
            null -> AuthStatus.NotAuthenticated
            else -> {
                val userInfo = loadUserInfoByName(username = username)
                AuthStatus.Authenticated(userInfo = userInfo)
            }
        }
    }

    private suspend fun loadUserInfoByName(username: String): UserInfo {
        val mapper = UserEntityToUiModelMapper()
        val entity = repo.getUserByName(username)!!
        return mapper.invoke(entity)
    }

    companion object {
        fun init(context: Context) {
            AppModuleImpl.init(context)
        }

        val instance get() = AppModule.instance.authManager
    }

}