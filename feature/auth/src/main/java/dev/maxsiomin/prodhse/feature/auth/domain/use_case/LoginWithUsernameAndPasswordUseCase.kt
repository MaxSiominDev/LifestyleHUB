package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.AuthError
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import javax.inject.Inject

internal class LoginWithUsernameAndPasswordUseCase @Inject constructor(
    private val usersRepo: UsersRepository,
) {

    suspend operator fun invoke(username: String, password: String): Resource<Unit, AuthError.Login> {
        return usersRepo.loginWithUsernameAndPassword(username = username, password = password)
    }

}