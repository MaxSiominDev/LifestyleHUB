package dev.maxsiomin.prodhse.core.data

import dev.maxsiomin.prodhse.core.domain.NetworkError
import dev.maxsiomin.prodhse.core.domain.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get

suspend inline fun <reified T> HttpClient.safeGet(
    requestBuilder: HttpRequestBuilder.() -> Unit
): Resource<T, NetworkError> {
    return try {
        val response: T? = this.get { requestBuilder() }.body()
        if (response == null) {
            Resource.Error(NetworkError.EmptyResponse)
        } else {
            Resource.Success(response)
        }
    } catch (e: RedirectResponseException) {
        Resource.Error(NetworkError.Redirected)
    } catch (e: ClientRequestException) {
        Resource.Error(NetworkError.InvalidRequest)
    } catch (e: ServerResponseException) {
        Resource.Error(NetworkError.Server)
    } catch (e: Exception) {
        Resource.Error(NetworkError.Unknown(e.message))
    }
}