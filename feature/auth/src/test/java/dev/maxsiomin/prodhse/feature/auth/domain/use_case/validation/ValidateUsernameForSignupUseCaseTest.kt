package dev.maxsiomin.prodhse.feature.auth.domain.use_case.validation

import dev.maxsiomin.common.domain.resource.Resource
import org.junit.Test

class ValidateUsernameForSignupUseCaseTest {

    private val validator = ValidateUsernameForSignupUseCase()

    @Test
    fun `too short username fails`() {
        val length = ValidateUsernameForSignupUseCase.MIN_USERNAME_LENGTH - 1
        val shortUsername = buildString {
            repeat(length) {
                append("a")
            }
        }
        validator(shortUsername) as Resource.Error
    }

    @Test
    fun `too long username fails`() {
        val length = ValidateUsernameForSignupUseCase.MAX_USERNAME_LENGTH + 1
        val longUsername = buildString {
            repeat(length) {
                append("a")
            }
        }
        validator(longUsername) as Resource.Error
    }

    @Test
    fun `valid username succeeds`() {
        val length = ValidateUsernameForSignupUseCase.MAX_USERNAME_LENGTH - 1
        val validUsername = buildString {
            repeat(length) {
                append("a")
            }
        }
        validator(validUsername) as Resource.Success
    }

}