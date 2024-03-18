package dev.maxsiomin.authlib.domain

sealed class LoginStatus {
    data object Success : LoginStatus()
    data object Failure : LoginStatus()
}