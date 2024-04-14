package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.prodhse.feature.auth.R
import javax.inject.Inject

class ValidatePasswordForLogin @Inject constructor() {

    fun execute(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.blank_password)
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}