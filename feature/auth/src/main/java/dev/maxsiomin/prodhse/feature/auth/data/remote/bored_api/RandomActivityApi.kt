package dev.maxsiomin.prodhse.feature.auth.data.remote.bored_api

import dev.maxsiomin.common.domain.NetworkError
import dev.maxsiomin.common.domain.Resource
import dev.maxsiomin.prodhse.feature.auth.data.dto.bored.BoredApiResponse

interface RandomActivityApi {

    suspend fun getActivity(): Resource<BoredApiResponse, NetworkError>

}