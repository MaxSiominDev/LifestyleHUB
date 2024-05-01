package dev.maxsiomin.prodhse.feature.auth.data.repository

import dev.maxsiomin.common.data.ToDomainMapper
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.data.dto.random_user.Result
import dev.maxsiomin.prodhse.feature.auth.data.remote.random_user.RandomUserApi
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomUser
import dev.maxsiomin.prodhse.feature.auth.domain.repository.RandomUserRepository
import javax.inject.Inject

class RandomUserRepositoryImpl @Inject constructor(
    private val api: RandomUserApi,
    private val randomUserMapper: ToDomainMapper<Result, RandomUser>,
) : RandomUserRepository {

    override suspend fun getRandomUser(): Resource<RandomUser, NetworkError> {
        val apiResponse = api.getRandomUser()
        return when (apiResponse) {
            is Resource.Error -> Resource.Error(apiResponse.error)
            is Resource.Success -> {
                apiResponse.data.results.firstOrNull()?.let(randomUserMapper::toDomain)?.let {
                    Resource.Success(it)
                } ?: Resource.Error(NetworkError.EmptyResponse)
            }
        }
    }

}