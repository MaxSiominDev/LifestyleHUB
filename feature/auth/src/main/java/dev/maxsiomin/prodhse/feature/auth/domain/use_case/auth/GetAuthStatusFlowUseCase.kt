package dev.maxsiomin.prodhse.feature.auth.domain.use_case.auth

import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GetAuthStatusFlowUseCase @Inject constructor(private val repo: UsersRepository) {

    operator fun invoke(): Flow<AuthStatus>  {
        return repo.getAuthStatusFlow()
    }

}