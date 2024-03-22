package dev.maxsiomin.authlib.domain

sealed class RegistrationStatus {
    data object Success : RegistrationStatus()
    data class Failure(val reason: String) : RegistrationStatus()
}