package dev.maxsiomin.prodhse.feature.auth.domain.use_case.validation

import dev.maxsiomin.common.domain.resource.Error
import dev.maxsiomin.common.domain.resource.Resource
import javax.inject.Inject

internal class ValidatePasswordForLoginUseCase @Inject constructor()  {

    operator fun invoke(password: String): Resource<Unit, PasswordForLoginError> {
        if (password.isBlank()) {
            return Resource.Error(PasswordForLoginError.IsBlank)
        }
        return Resource.Success(Unit)
    }

    sealed interface PasswordForLoginError : Error {
        data object IsBlank : PasswordForLoginError
    }

}