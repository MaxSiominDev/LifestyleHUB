package dev.maxsiomin.prodhse.feature.auth.data.repository

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.data.mappers.RandomActivityDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.auth.data.remote.bored_api.RandomActivityApi
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomActivity
import dev.maxsiomin.prodhse.feature.auth.domain.repository.RandomActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RandomActivityRepositoryImpl @Inject constructor(
    private val api: RandomActivityApi
): RandomActivityRepository {

    override suspend fun getRandomActivity(): Flow<Resource<RandomActivity, NetworkError>> {
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