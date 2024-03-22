package dev.maxsiomin.prodhse.feature.auth.data.repository

import dev.maxsiomin.prodhse.core.Resource
import dev.maxsiomin.prodhse.core.asResult
import dev.maxsiomin.prodhse.feature.auth.data.mappers.RandomUserDtoToModelMapper
import dev.maxsiomin.prodhse.feature.auth.data.remote.RandomUserApi
import dev.maxsiomin.prodhse.feature.auth.domain.RandomUserModel
import dev.maxsiomin.prodhse.feature.auth.domain.repository.RandomUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class RandomUserRepositoryImpl @Inject constructor(
    private val api: RandomUserApi
) : RandomUserRepository {

    override suspend fun getRandomUser(): Flow<Resource<RandomUserModel>> {
        return flow {
            val apiResponse = api.getRandomUser()
            val remoteData = apiResponse.response?.results?.firstOrNull()
            if (remoteData != null) {
                val mapper = RandomUserDtoToModelMapper()
                emit(mapper.invoke(remoteData))
            } else {
                throw (apiResponse.error ?: Exception("Unknown error"))
            }
        }.asResult()
    }

}