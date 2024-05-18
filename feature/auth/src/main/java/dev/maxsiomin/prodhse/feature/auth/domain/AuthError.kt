package dev.maxsiomin.prodhse.feature.auth.domain

import dev.maxsiomin.common.domain.resource.Error

internal sealed interface AuthError : Error {

    sealed interface Login : AuthError {
        data class Unknown(val reason: String?) : Login
    }

    sealed interface Signup : AuthError {
        data class Unknown(val reason: String?) : Signup
    }

}
