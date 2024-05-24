package dev.maxsiomin.common.util

import android.content.Context
import com.google.common.truth.Truth.assertThat
import dev.maxsiomin.common.presentation.UiText
import io.mockk.mockk
import org.junit.Test

class TextFieldStateTest {

    private val context: Context = mockk()

    @Test
    fun `updateError should update the error`() {
        val initialState = TextFieldState.new(error = UiText.DynamicString("Initial error"))

        val updatedState = initialState.updateError(UiText.DynamicString("New error"))

        assertThat(updatedState.error?.asString(context)).isEqualTo("New error")
    }

    @Test
    fun `resetError should reset the error`() {
        val initialState = TextFieldState.new(error = UiText.DynamicString("Initial error"))

        val updatedState = initialState.resetError()

        assertThat(updatedState.error).isNull()
    }

    @Test
    fun `updateText should update the text`() {
        val initialState = TextFieldState.new(text = "Initial text")

        val updatedState = initialState.updateText("New text")

        assertThat(updatedState.text).isEqualTo("New text")
    }

    @Test
    fun `resetText should reset the text`() {
        val initialState = TextFieldState.new(text = "Initial text")

        val updatedState = initialState.resetText()

        assertThat(updatedState.text).isEmpty()
    }

}