package dev.maxsiomin.prodhse.feature.auth.domain.use_case.validation

import dev.maxsiomin.common.domain.resource.Error
import dev.maxsiomin.common.domain.resource.Resource
import javax.inject.Inject

internal class ValidateUsernameForSignupUseCase @Inject constructor() {

    operator fun invoke(username: String): Resource<Unit, UsernameForSignupError> {
        if (username.length !in MIN_USERNAME_LENGTH..MAX_USERNAME_LENGTH) {
            return Resource.Error(UsernameForSignupError.InvalidLength)
        }
        return Resource.Success(Unit)
    }

    sealed interface UsernameForSignupError : Error {
        data object InvalidLength : UsernameForSignupError
    }

    companion object {
        const val MIN_USERNAME_LENGTH = 6
        const val MAX_USERNAME_LENGTH = 20
    }

}