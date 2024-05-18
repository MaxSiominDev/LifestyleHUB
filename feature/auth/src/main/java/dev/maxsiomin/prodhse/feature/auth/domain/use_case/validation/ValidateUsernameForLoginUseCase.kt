package dev.maxsiomin.prodhse.feature.auth.domain.use_case.validation

import androidx.annotation.VisibleForTesting
import dev.maxsiomin.common.domain.resource.Error
import dev.maxsiomin.common.domain.resource.Resource
import javax.inject.Inject

class ValidateUsernameForLoginUseCase @Inject constructor() {

    operator fun invoke(username: String): Resource<Unit, UsernameForLoginError> {
        if (username.isBlank()) {
            return Resource.Error(UsernameForLoginError.IsBlank)
        }
        return Resource.Success(Unit)
    }

    sealed interface UsernameForLoginError : Error {
        data object IsBlank : UsernameForLoginError
    }

}
