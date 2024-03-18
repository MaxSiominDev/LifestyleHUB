package dev.maxsiomin.prodhse.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.asResult(): Flow<Resource<T>> {
    return this
        .map<T, Resource<T>> { Resource.Success(it) }
        .onStart { emit(Resource.Loading(isLoading = true)) }
        .catch { emit(Resource.Error(exception = it)) }
        .onCompletion { emit(Resource.Loading(isLoading = false)) }
}
