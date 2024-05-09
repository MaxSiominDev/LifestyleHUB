package dev.maxsiomin.prodhse.feature.auth.domain.use_case.auth

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomUserData
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GetRandomUserDataUseCase @Inject constructor(private val usersRepo: UsersRepository) {

    suspend operator fun invoke(): Resource<RandomUserData, NetworkError> =
        withContext(Dispatchers.IO) {
            return@withContext usersRepo.getRandomUserData()
        }

}