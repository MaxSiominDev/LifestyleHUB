package dev.maxsiomin.prodhse.feature.auth.domain.use_case.auth

import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class CheckIfUsernameExistsUseCase @Inject constructor(private val repo: UsersRepository) {

    suspend operator fun invoke(username: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext repo.checkIfUsernameExists(username = username)
    }

}