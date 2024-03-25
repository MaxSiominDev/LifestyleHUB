package dev.maxsiomin.prodhse.core.util

sealed class Resource<T>() {
    class Success<T>(val data: T): Resource<T>()
    class Error<T>(val exception: Throwable): Resource<T>()
    class Loading<T>(val isLoading: Boolean = true): Resource<T>()
}
