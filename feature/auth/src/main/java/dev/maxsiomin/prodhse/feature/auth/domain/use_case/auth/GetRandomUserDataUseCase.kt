package dev.maxsiomin.prodhse.feature.auth.domain.use_case.auth

import dev.maxsiomin.common.domain.resource.DataError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.core.util.DispatcherProvider
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomUserData
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GetRandomUserDataUseCase @Inject constructor(
    private val usersRepo: UsersRepository,
    private val dispatchers: DispatcherProvider,
) {

    suspend operator fun invoke(): Resource<RandomUserData, DataError> =
        withContext(dispatchers.io) {
            return@withContext usersRepo.getRandomUserData()
        }

}