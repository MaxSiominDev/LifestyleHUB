package dev.maxsiomin.prodhse.core.util

data class ResponseWithException<T, E : Exception> (
    val response: T?,
    val error: E?,
)