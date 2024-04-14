package dev.maxsiomin.common.presentation

data class SnackbarInfo(
    val message: UiText,
)

typealias SnackbarCallback = (SnackbarInfo) -> Unit
