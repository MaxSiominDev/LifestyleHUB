package dev.maxsiomin.prodhse.feature.auth.domain.use_case.validation

import dev.maxsiomin.common.domain.resource.Resource
import org.junit.Test

class ValidateUsernameForSignupUseCaseTest {

    private val validator = ValidateUsernameForSignupUseCase()

    @Test
    fun `too short username fails`() {
        validator("test") as Resource.Error
    }

    @Test
    fun `too long username fails`() {
        validator("test test test test test")
    }

    @Test
    fun `valid username succeeds`() {
        validator("test test") as Resource.Success
    }

}