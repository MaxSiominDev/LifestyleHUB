package dev.maxsiomin.prodhse.core

import java.lang.Exception

sealed class Resource<T>(val data: T? = null, val exception: Throwable? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(exception: Throwable, data: T? = null): Resource<T>(data, exception)
    class Loading<T>(val isLoading: Boolean = true): Resource<T>()
}
