package dev.maxsiomin.prodhse.feature.auth.domain.use_case.auth

import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.core.util.DispatcherProvider
import dev.maxsiomin.prodhse.feature.auth.domain.AuthError
import dev.maxsiomin.prodhse.feature.auth.domain.model.RegistrationInfo
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class RegisterUseCase @Inject constructor(
    private val usersRepo: UsersRepository,
    private val dispatchers: DispatcherProvider,
) {

    suspend operator fun invoke(
        username: String,
        password: String,
        fullname: String,
        avatarUrl: String,
    ): Resource<Unit, AuthError.Signup> = withContext(dispatchers.io) {
        val registrationInfo = RegistrationInfo(
            username = username,
            password = password,
            fullName = fullname,
            avatarUrl = avatarUrl,
        )
        return@withContext usersRepo.signupWithUsernameAndPassword(registrationInfo)
    }

}