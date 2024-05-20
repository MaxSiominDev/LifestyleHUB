package dev.maxsiomin.prodhse.feature.auth.domain.use_case.validation

import dev.maxsiomin.common.domain.resource.Resource
import org.junit.Test

class ValidatePasswordForSignupUseCaseTest {

    private val validator = ValidatePasswordForSignupUseCase()

    @Test
    fun `too short password fails`() {
        val length = ValidatePasswordForSignupUseCase.MIN_PASSWORD_LENGTH - 1
        val shortPassword = buildString {
            append("A")
            repeat(length - 1) {
                append("1")
            }
        }
        validator(password = shortPassword) as Resource.Error
    }

    @Test
    fun `too long password fails`() {
        val length = ValidatePasswordForSignupUseCase.MAX_PASSWORD_LENGTH + 1
        val longPassword = buildString {
            append("A")
            repeat(length - 1) {
                append("1")
            }
        }
        validator(password = longPassword) as Resource.Error
    }

    @Test
    fun `no digits password fails`() {
        validator("Qwerty####") as Resource.Error
    }

    @Test
    fun `no letters password fails`() {
        validator("1234567890") as Resource.Error
    }

    @Test
    fun `valid password succeeds`() {
        val length = ValidatePasswordForSignupUseCase.MIN_PASSWORD_LENGTH + 1
        val validPassword = buildString {
            append("A")
            repeat(length - 1) {
                append("1")
            }
        }
        validator(validPassword) as Resource.Success
    }

}