package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.prodhse.core.util.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)
