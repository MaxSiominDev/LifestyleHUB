package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.AuthError
import dev.maxsiomin.prodhse.feature.auth.domain.model.RegistrationInfo
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import javax.inject.Inject

internal class RegisterUseCase @Inject constructor(private val usersRepo: UsersRepository) {

    suspend operator fun invoke(
        username: String,
        password: String,
        fullname: String,
        avatarUrl: String,
    ): Resource<Unit, AuthError.Signup> {
        val registrationInfo = RegistrationInfo(
            username = username,
            password = password,
            fullName = fullname,
            avatarUrl = avatarUrl,
        )
        return usersRepo.signupWithUsernameAndPassword(registrationInfo)
    }

}