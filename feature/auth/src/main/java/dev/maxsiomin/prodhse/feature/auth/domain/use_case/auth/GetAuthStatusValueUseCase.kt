package dev.maxsiomin.prodhse.feature.auth.domain.use_case.auth

import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import javax.inject.Inject

internal class GetAuthStatusValueUseCase @Inject constructor(private val usersRepo: UsersRepository) {

    operator fun invoke(): AuthStatus {
        return usersRepo.getAuthStatus()
    }

}