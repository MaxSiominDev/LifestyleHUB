package dev.maxsiomin.common.util

import dev.maxsiomin.common.presentation.UiText

data class TextFieldState(
    val text: String = "",
    val error: UiText? = null,
)

fun TextFieldState.updateError(error: UiText?): TextFieldState {
    return this.copy(text = this.text, error = error)
}

fun TextFieldState.resetError(): TextFieldState {
    return this.copy(text = this.text, error = null)
}
