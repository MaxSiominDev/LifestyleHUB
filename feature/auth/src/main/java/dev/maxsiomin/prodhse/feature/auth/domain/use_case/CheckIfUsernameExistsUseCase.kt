package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import javax.inject.Inject

class CheckIfUsernameExistsUseCase @Inject constructor(private val repo: UsersRepository) {

    suspend operator fun invoke(username: String): Boolean {
        return repo.checkIfUsernameExists(username = username)
    }

}