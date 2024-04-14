package dev.maxsiomin.prodhse.feature.auth.domain.repository

import dev.maxsiomin.prodhse.core.domain.NetworkError
import dev.maxsiomin.prodhse.core.domain.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.RandomActivityModel
import kotlinx.coroutines.flow.Flow

interface RandomActivityRepository {

    suspend fun getRandomActivity(): Flow<Resource<RandomActivityModel, NetworkError>>

}