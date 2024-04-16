package dev.maxsiomin.prodhse.feature.auth.data.remote.bored_api

import dev.maxsiomin.common.data.safeGet
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.data.dto.bored.BoredApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.url
import javax.inject.Inject

class RandomActivityApiImpl @Inject constructor(private val client: HttpClient) : RandomActivityApi {

    override suspend fun getActivity(): Resource<BoredApiResponse, NetworkError> {
        return client.safeGet {
            url(HttpRoutes.GET_ACTIVITY)
        }
    }

}