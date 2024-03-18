package dev.maxsiomin.prodhse.core

data class ResponseWithMessage<T, E : Exception> (
    val response: T?,
    val error: E?,
)