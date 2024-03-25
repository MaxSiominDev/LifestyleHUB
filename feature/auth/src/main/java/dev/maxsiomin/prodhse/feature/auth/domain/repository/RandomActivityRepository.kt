package dev.maxsiomin.prodhse.feature.auth.domain.repository

import dev.maxsiomin.prodhse.core.util.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.RandomActivityModel
import kotlinx.coroutines.flow.Flow

interface RandomActivityRepository {

    suspend fun getRandomActivity(): Flow<Resource<RandomActivityModel>>

}