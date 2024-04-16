package dev.maxsiomin.prodhse.feature.auth.domain.repository

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.RandomUserModel
import kotlinx.coroutines.flow.Flow

interface RandomUserRepository {

    suspend fun getRandomUser(): Flow<Resource<RandomUserModel, NetworkError>>

}