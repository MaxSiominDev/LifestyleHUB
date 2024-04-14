package dev.maxsiomin.prodhse.core.domain

sealed interface NetworkError : Error {

    data object EmptyResponse : NetworkError
    data object Redirected : NetworkError
    data object InvalidRequest : NetworkError
    data object Server : NetworkError
    data class Unknown(val message: String?) : NetworkError

}
