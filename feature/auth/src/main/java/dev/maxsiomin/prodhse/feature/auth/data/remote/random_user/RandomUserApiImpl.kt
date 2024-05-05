package dev.maxsiomin.prodhse.feature.auth.data.remote.random_user

import dev.maxsiomin.common.data.safeGet
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.data.dto.random_user.RandomUserResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.url
import javax.inject.Inject

internal class RandomUserApiImpl @Inject constructor(private val client: HttpClient) : RandomUserApi {

    override suspend fun getRandomUser(): Resource<RandomUserResponse, NetworkError> {
        return client.safeGet {
            url(HttpRoutes.GET_RANDOM_USER)
        }
    }

}