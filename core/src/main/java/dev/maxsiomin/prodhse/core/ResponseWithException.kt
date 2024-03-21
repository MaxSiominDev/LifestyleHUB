package dev.maxsiomin.prodhse.core

data class ResponseWithException<T, E : Exception> (
    val response: T?,
    val error: E?,
)