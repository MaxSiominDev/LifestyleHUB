package dev.maxsiomin.prodhse.feature.auth.data.remote.bored_api

import dev.maxsiomin.prodhse.core.util.ResponseWithException
import dev.maxsiomin.prodhse.feature.auth.data.dto.bored.BoredApiResponse

interface RandomActivityApi {

    suspend fun getActivity(): ResponseWithException<BoredApiResponse, Exception>

}