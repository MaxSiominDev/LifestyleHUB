package dev.maxsiomin.prodhse.feature.auth.domain.use_case.auth

import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetAuthStatusFlowUseCase @Inject constructor(private val repo: UsersRepository) {

    operator fun invoke(): Flow<AuthStatus>  {
        return repo.getAuthStatusStream()
    }

}