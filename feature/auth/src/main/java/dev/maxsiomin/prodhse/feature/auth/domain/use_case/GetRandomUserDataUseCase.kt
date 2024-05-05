package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomUserData
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import javax.inject.Inject

internal class GetRandomUserDataUseCase @Inject constructor(private val usersRepo: UsersRepository) {

    suspend operator fun invoke(): Resource<RandomUserData, NetworkError> {
        return usersRepo.getRandomUserData()
    }

}