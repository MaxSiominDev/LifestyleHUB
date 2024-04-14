package dev.maxsiomin.prodhse.feature.auth.data.remote.bored_api

import dev.maxsiomin.prodhse.core.data.safeGet
import dev.maxsiomin.prodhse.core.domain.NetworkError
import dev.maxsiomin.prodhse.core.domain.Resource
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