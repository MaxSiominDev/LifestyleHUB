package dev.maxsiomin.authlib.domain

sealed class LoginStatus {
    data object Success : LoginStatus()
    data class Failure(val reason: String) : LoginStatus()
}