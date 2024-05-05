package dev.maxsiomin.prodhse.feature.auth.data.remote.random_user

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.data.dto.random_user.RandomUserResponse

internal interface RandomUserApi {

    suspend fun getRandomUser(): Resource<RandomUserResponse, NetworkError>

}