package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import javax.inject.Inject

internal class LogoutUseCase @Inject constructor(private val usersRepo: UsersRepository) {

    suspend operator fun invoke() {
        usersRepo.logout()
    }

}