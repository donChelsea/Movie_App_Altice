package com.ckatsidzira.domain.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true): Resource<T>(null)
}

inline fun <T> safeFlow(crossinline block: suspend () -> T): Flow<Resource<T>> = flow {
    emit(Resource.Loading())
    emit(Resource.Success(block()))
}.catch { e ->
    emit(Resource.Error(message = e.localizedMessage ?: "An error occurred"))
}