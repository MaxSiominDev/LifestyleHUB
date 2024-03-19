package dev.maxsiomin.authlib.domain

sealed class AuthStatus {
    data class Authenticated(val userInfo: UserInfo) : AuthStatus()
    data object NotAuthenticated : AuthStatus()
    data object Loading : AuthStatus()
}