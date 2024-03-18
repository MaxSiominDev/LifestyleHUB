package dev.maxsiomin.authlib.domain

public sealed class AuthStatus {
    public data class Authenticated(val userInfo: UserInfo) : AuthStatus()
    public data object NotAuthenticated : AuthStatus()
    public data object Loading : AuthStatus()
}