package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.prodhse.core.util.UiText
import dev.maxsiomin.prodhse.feature.auth.R
import javax.inject.Inject

class ValidateUsernameForSignup @Inject constructor() {

    fun execute(username: String): ValidationResult {
        if (username.length < MIN_USERNAME_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    R.string.username_length_violated,
                    MIN_USERNAME_LENGTH,
                    MAX_USERNAME_LENGTH,
                )
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    companion object {
        const val MIN_USERNAME_LENGTH = 6
        const val MAX_USERNAME_LENGTH = 20
    }

}