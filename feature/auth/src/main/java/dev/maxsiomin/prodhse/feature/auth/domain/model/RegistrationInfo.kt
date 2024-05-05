package dev.maxsiomin.prodhse.feature.auth.domain.model

internal data class RegistrationInfo(
    val username: String,
    val password: String,
    val avatarUrl: String,
    val fullName: String,
)
