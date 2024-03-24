package dev.maxsiomin.prodhse.core.util

data class SnackbarInfo(
    val message: UiText,
)

typealias SnackbarCallback = (SnackbarInfo) -> Unit
