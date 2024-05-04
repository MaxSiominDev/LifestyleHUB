package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import javax.inject.Inject

class GetAuthStatusValueUseCase @Inject constructor(private val repo: UsersRepository) {

    operator fun invoke(): AuthStatus {
        return repo.getAuthStatus()
    }

}