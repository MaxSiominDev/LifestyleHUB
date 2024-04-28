package dev.maxsiomin.authlib.domain.model

data class RegistrationInfo(
    val username: String,
    val password: String,
    val avatarUrl: String,
    val fullName: String,
)
