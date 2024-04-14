package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.prodhse.feature.auth.R
import javax.inject.Inject

class ValidateUsernameForLogin @Inject constructor(){

    fun execute(username: String): ValidationResult {
        if (username.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.blank_username)
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}
