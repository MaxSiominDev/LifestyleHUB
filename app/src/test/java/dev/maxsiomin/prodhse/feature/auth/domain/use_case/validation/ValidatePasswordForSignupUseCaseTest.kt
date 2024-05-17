package dev.maxsiomin.prodhse.feature.auth.domain.use_case.validation

import dev.maxsiomin.common.domain.resource.Resource
import org.junit.Test

class ValidatePasswordForSignupUseCaseTest {

    private val validator = ValidatePasswordForSignupUseCase()

    @Test
    fun `too short password fails`() {
        validator(password = "Aa1#") as Resource.Error
    }

    @Test
    fun `too long password fails`() {
        // 24 symbols
        validator(password = "Aa1#Aa1#Aa1#Aa1#Aa1#Aa1#") as Resource.Error
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
        validator("Qwerty1#") as Resource.Success
    }

}