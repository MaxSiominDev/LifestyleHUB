package dev.maxsiomin.common.domain.resource

sealed interface DataError : Error

sealed interface NetworkError : DataError {

    data object EmptyResponse : NetworkError
    data object Redirected : NetworkError
    data object InvalidRequest : NetworkError
    data object Server : NetworkError
    data class Unknown(val message: String?) : NetworkError

}

sealed interface LocalError : DataError {

    data class Unknown(val message: String?) : LocalError

}
