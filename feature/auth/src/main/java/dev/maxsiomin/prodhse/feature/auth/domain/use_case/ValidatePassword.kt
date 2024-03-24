package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.prodhse.core.util.UiText
import dev.maxsiomin.prodhse.feature.auth.R
import javax.inject.Inject

class ValidatePassword @Inject constructor() {

    fun execute(password: String): ValidationResult {
        if (password.length < MIN_PASSWORD_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.password_length_violated, MIN_PASSWORD_LENGTH)
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.password_must_consist_of_letters_and_digits)
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 8
    }

}