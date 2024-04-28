package dev.maxsiomin.authlib.domain.model

data class UserInfo(
    val username: String,
    val passwordHash: String,
    val avatarUrl: String,
    val fullName: String,
)