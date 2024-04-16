package dev.maxsiomin.prodhse.feature.auth.data.repository

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.data.mappers.RandomUserDtoToModelMapper
import dev.maxsiomin.prodhse.feature.auth.data.remote.random_user.RandomUserApi
import dev.maxsiomin.prodhse.feature.auth.domain.RandomUserModel
import dev.maxsiomin.prodhse.feature.auth.domain.repository.RandomUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RandomUserRepositoryImpl @Inject constructor(
    private val api: RandomUserApi
) : RandomUserRepository {

    override suspend fun getRandomUser(): Flow<Resource<RandomUserModel, NetworkError>> {
        return flow {
            val apiResponse = api.getRandomUser()
            when (apiResponse) {
                is Resource.Error -> emit(Resource.Error(apiResponse.error))
                is Resource.Success -> {
                    val mapper = RandomUserDtoToModelMapper()
                    apiResponse.data.results.firstOrNull()?.let(mapper)?.let {
                        emit(Resource.Success(it))
                    } ?: emit(Resource.Error(NetworkError.EmptyResponse))
                }
            }
        }
    }

}