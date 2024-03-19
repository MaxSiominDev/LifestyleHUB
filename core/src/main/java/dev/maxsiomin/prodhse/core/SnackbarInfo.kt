package dev.maxsiomin.prodhse.core

data class SnackbarInfo(
    val message: UiText,
)

typealias SnackbarCallback = (SnackbarInfo) -> Unit
