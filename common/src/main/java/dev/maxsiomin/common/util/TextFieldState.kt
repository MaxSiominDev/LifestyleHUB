package dev.maxsiomin.common.util

import dev.maxsiomin.common.presentation.UiText

class TextFieldState private constructor(
    val text: String,
    val error: UiText?,
) {
    companion object {
        fun new(text: String = "", error: UiText? = null): TextFieldState {
            return TextFieldState(text, error)
        }
    }
}

fun TextFieldState.updateError(error: UiText?): TextFieldState {
    return TextFieldState.new(text = this.text, error = error)
}

fun TextFieldState.resetError(): TextFieldState {
    return TextFieldState.new(text = this.text, error = null)
}

fun TextFieldState.updateText(text: String): TextFieldState {
    return TextFieldState.new(text = text, error = this.error)
}

fun TextFieldState.resetText(): TextFieldState {
    return TextFieldState.new(text = "", error = this.error)
}
