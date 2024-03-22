package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.prodhse.core.UiText
import dev.maxsiomin.prodhse.feature.auth.R
import javax.inject.Inject

class ValidateUsername @Inject constructor() {

    fun execute(username: String): ValidationResult {
        if (username.length < MIN_USERNAME_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    R.string.username_length_violated,
                    MIN_USERNAME_LENGTH
                )
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    companion object {
        const val MIN_USERNAME_LENGTH = 6
    }

}