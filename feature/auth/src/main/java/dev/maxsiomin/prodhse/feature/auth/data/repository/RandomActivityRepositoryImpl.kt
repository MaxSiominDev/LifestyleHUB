package dev.maxsiomin.prodhse.feature.auth.data.repository

import dev.maxsiomin.prodhse.core.domain.NetworkError
import dev.maxsiomin.prodhse.core.domain.Resource
import dev.maxsiomin.prodhse.feature.auth.data.mappers.RandomActivityDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.auth.data.remote.bored_api.RandomActivityApi
import dev.maxsiomin.prodhse.feature.auth.domain.RandomActivityModel
import dev.maxsiomin.prodhse.feature.auth.domain.repository.RandomActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RandomActivityRepositoryImpl @Inject constructor(
    private val api: RandomActivityApi
): RandomActivityRepository {

    override suspend fun getRandomActivity(): Flow<Resource<RandomActivityModel, NetworkError>> {
        return flow {
            val apiResponse = api.getActivity()
            val mapper = RandomActivityDtoToUiModelMapper()
            when (apiResponse) {
                is Resource.Error -> emit(Resource.Error(apiResponse.error))
                is Resource.Success -> {
                    apiResponse.data.let(mapper)?.let {
                        emit(Resource.Success(it))
                    } ?: emit(Resource.Error(NetworkError.EmptyResponse))
                }
            }
        }
    }

}