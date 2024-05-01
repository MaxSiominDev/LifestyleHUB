package dev.maxsiomin.authlib.domain

import dev.maxsiomin.authlib.domain.model.UserInfo

sealed class AuthStatus {
    data class Authenticated(val userInfo: UserInfo) : AuthStatus()
    data object NotAuthenticated : AuthStatus()
    data object Loading : AuthStatus()
}