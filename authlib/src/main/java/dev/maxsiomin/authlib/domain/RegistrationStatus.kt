package dev.maxsiomin.authlib.domain

sealed class RegistrationStatus {
    data object Success : RegistrationStatus()
    data object Failure : RegistrationStatus()
}