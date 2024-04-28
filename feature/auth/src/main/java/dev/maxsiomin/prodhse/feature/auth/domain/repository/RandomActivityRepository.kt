package dev.maxsiomin.prodhse.feature.auth.domain.repository

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomActivity
import kotlinx.coroutines.flow.Flow

interface RandomActivityRepository {

    suspend fun getRandomActivity(): Resource<RandomActivity, NetworkError>

}