package dev.maxsiomin.prodhse.feature.auth.domain.use_case.validation

import dev.maxsiomin.common.domain.resource.Resource
import org.junit.Test

class ValidatePasswordForLoginUseCaseTest {

    private val validator = ValidatePasswordForLoginUseCase()

    @Test
    fun `blank password fails`() {
        validator(password = "") as Resource.Error
    }

    @Test
    fun `any non-empty string returns succeeds`() {
        validator(password = "test") as Resource.Success
    }

}