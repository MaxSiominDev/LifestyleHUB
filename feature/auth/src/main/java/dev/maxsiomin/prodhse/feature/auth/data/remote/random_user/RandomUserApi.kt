package dev.maxsiomin.prodhse.feature.auth.data.remote.random_user

import dev.maxsiomin.common.domain.NetworkError
import dev.maxsiomin.common.domain.Resource
import dev.maxsiomin.prodhse.feature.auth.data.dto.random_user.RandomUserResponse

interface RandomUserApi {

    suspend fun getRandomUser(): Resource<RandomUserResponse, NetworkError>

}