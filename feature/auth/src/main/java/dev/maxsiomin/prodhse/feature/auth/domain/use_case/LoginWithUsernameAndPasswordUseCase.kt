package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.AuthError
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import javax.inject.Inject

class LoginWithUsernameAndPasswordUseCase @Inject constructor(
    private val repo: UsersRepository,
) {

    suspend operator fun invoke(username: String, password: String): Resource<Unit, AuthError.Login> {
        return repo.loginWithUsernameAndPassword(username = username, password = password)
    }

}