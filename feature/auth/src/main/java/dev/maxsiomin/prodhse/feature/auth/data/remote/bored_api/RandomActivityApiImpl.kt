package dev.maxsiomin.prodhse.feature.auth.data.remote.bored_api

import dev.maxsiomin.prodhse.core.util.ResponseWithException
import dev.maxsiomin.prodhse.feature.auth.data.dto.bored.BoredApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.url
import timber.log.Timber
import javax.inject.Inject

class RandomActivityApiImpl @Inject constructor(private val client: HttpClient) : RandomActivityApi {

    override suspend fun getActivity(): ResponseWithException<BoredApiResponse, Exception> {
        try {
            val response: BoredApiResponse? = client.get {
                url(HttpRoutes.GET_ACTIVITY)
            }.body()
            if (response == null) {
                Timber.e("Response is null")
                return ResponseWithException(null, Exception("Response is null"))
            }
            return ResponseWithException(response, null)
        } catch (e: RedirectResponseException) {
            Timber.e(e.response.status.description)
            return ResponseWithException(null, e)
        } catch (e: ClientRequestException) {
            Timber.e(e.response.status.description)
            return ResponseWithException(null, e)
        } catch (e: ServerResponseException) {
            Timber.e(e.response.status.description)
            return ResponseWithException(null, e)
        } catch (e: Exception) {
            Timber.e(e.message)
            return ResponseWithException(null, e)
        }
    }

}