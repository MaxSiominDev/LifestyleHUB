package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.common.domain.resource.Error
import dev.maxsiomin.common.domain.resource.Resource
import javax.inject.Inject

class ValidatePasswordForSignup @Inject constructor() {

    fun execute(password: String): Resource<Unit, PasswordForSignupError> {
        if (password.length !in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH) {
            return Resource.Error(PasswordForSignupError.InvalidLength)
            /*return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.password_length_violated, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH)
            )*/
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            /*return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.password_must_consist_of_letters_and_digits)
            )*/
            return Resource.Error(PasswordForSignupError.TooWeak)
        }
        return Resource.Success(Unit)
    }

    sealed interface PasswordForSignupError : Error {
        data object InvalidLength : PasswordForSignupError
        data object TooWeak : PasswordForSignupError
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 8
        const val MAX_PASSWORD_LENGTH = 20
    }

}