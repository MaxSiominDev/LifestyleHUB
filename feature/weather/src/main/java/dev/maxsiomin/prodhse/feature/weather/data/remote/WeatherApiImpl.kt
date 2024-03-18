package dev.maxsiomin.prodhse.feature.weather.data.remote

import dev.maxsiomin.prodhse.core.ApiKeys
import dev.maxsiomin.prodhse.core.ResponseWithMessage
import dev.maxsiomin.prodhse.feature.weather.data.dto.current_weather_response.CurrentWeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import timber.log.Timber
import javax.inject.Inject

internal class WeatherApiImpl @Inject constructor(private val client: HttpClient) : WeatherApi {

    override suspend fun getCurrentWeather(lat: String, lon: String, lang: String): ResponseWithMessage<CurrentWeatherResponse, Exception> {
        try {
            val response: CurrentWeatherResponse? = client.get {
                url(HttpRoutes.CURRENT_WEATHER)
                parameter("lat", lat)
                parameter("lon", lon)
                parameter("lang", lang)
                parameter("appid", ApiKeys.OPEN_WEATHER_MAP)
            }.body()
            if (response == null) {
                Timber.e("Response is null")
                return ResponseWithMessage(null, Exception("Response is null"))
            }
            return ResponseWithMessage(response, null)
        } catch (e: RedirectResponseException) {
            Timber.e(e.response.status.description)
            return ResponseWithMessage(null, e)
        } catch (e: ClientRequestException) {
            Timber.e(e.response.status.description)
            return ResponseWithMessage(null, e)
        } catch (e: ServerResponseException) {
            Timber.e(e.response.status.description)
            return ResponseWithMessage(null, e)
        } catch (e: Exception) {
            Timber.e(e.message)
            return ResponseWithMessage(null, e)
        }

    }
}