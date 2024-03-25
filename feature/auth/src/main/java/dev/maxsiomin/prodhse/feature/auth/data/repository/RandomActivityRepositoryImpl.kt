package dev.maxsiomin.prodhse.feature.auth.data.repository

import dev.maxsiomin.prodhse.core.extensions.asResult
import dev.maxsiomin.prodhse.core.util.Resource
import dev.maxsiomin.prodhse.feature.auth.data.mappers.RandomActivityDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.auth.data.remote.bored_api.RandomActivityApi
import dev.maxsiomin.prodhse.feature.auth.domain.RandomActivityModel
import dev.maxsiomin.prodhse.feature.auth.domain.repository.RandomActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class RandomActivityRepositoryImpl @Inject constructor(
    private val api: RandomActivityApi
): RandomActivityRepository {

    override suspend fun getRandomActivity(): Flow<Resource<RandomActivityModel>> {
        return flow {
            val apiResponse = api.getActivity()
            val mapper = RandomActivityDtoToUiModelMapper()
            val remoteData = apiResponse.response?.let(mapper)
            if (remoteData != null) {
                emit(remoteData)
            } else {
                throw (apiResponse.error ?: Exception("Unknown error"))
            }
        }.asResult()
    }

}