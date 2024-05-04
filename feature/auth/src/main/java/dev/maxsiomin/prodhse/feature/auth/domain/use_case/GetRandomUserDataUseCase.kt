package dev.maxsiomin.prodhse.feature.auth.domain.use_case

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomUserData
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository
import javax.inject.Inject

class GetRandomUserDataUseCase @Inject constructor(private val repo: UsersRepository) {

    suspend operator fun invoke(): Resource<RandomUserData, NetworkError> {
        return repo.getRandomUserData()
    }

}