package dev.maxsiomin.authlib.domain

data class UserInfo(
    val username: String,
    val passwordHash: String,
)