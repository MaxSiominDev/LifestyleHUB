package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.common.presentation.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)
