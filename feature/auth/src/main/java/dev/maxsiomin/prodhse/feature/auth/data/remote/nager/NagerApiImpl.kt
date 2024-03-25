package dev.maxsiomin.prodhse.feature.auth.data.remote.nager

import dev.maxsiomin.prodhse.core.util.ResponseWithException
import dev.maxsiomin.prodhse.feature.auth.data.dto.nager.NagerResponseItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.url
import timber.log.Timber
import javax.inject.Inject

class NagerApiImpl @Inject constructor(private val client: HttpClient) : NagerApi {

    override suspend fun getHolidays(year: String, countryCode: String): ResponseWithException<List<NagerResponseItem?>, Exception> {
        try {
            val response: List<NagerResponseItem?>? = client.get {
                url(HttpRoutes.getHolidaysUrl(year = year, countryCode = countryCode))
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