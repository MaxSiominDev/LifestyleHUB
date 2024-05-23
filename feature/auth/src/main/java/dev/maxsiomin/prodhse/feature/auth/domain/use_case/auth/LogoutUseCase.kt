package dev.maxsiomin.prodhse.feature.auth.domain.use_case.auth

import dev.maxsiomin.prodhse.core.util.DispatcherProvider
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class LogoutUseCase @Inject constructor(
    private val usersRepo: UsersRepository,
    private val dispatchers: DispatcherProvider,
) {

    suspend operator fun invoke() = withContext(dispatchers.io) {
        usersRepo.logout()
    }

}