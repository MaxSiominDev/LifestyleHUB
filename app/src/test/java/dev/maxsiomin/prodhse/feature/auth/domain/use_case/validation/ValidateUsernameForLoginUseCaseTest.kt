package dev.maxsiomin.prodhse.feature.auth.domain.use_case.validation

import dev.maxsiomin.common.domain.resource.Resource
import org.junit.Test

class ValidateUsernameForLoginUseCaseTest {

    private val validator = ValidateUsernameForLoginUseCase()

    @Test
    fun `blank username fails`() {
        validator(username = "") as Resource.Error
    }

    @Test
    fun `any non-empty string succeeds`() {
        validator(username = "test") as Resource.Success
    }

}