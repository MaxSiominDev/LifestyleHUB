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

    override suspend fun getRandomActivity(): Resource<RandomActivity, NetworkError> {
        val apiResponse = api.getActivity()
        val mapper = RandomActivityDtoToUiModelMapper()
        return when (apiResponse) {
            is Resource.Error -> Resource.Error(apiResponse.error)
            is Resource.Success -> {
                apiResponse.data.let(mapper)?.let {
                    Resource.Success(it)
                } ?: Resource.Error(NetworkError.EmptyResponse)
            }
        }
    }

}