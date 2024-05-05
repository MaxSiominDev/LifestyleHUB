package dev.maxsiomin.prodhse.feature.home.data.remote.places_api

internal object HttpRoutes {

    private const val BASE_URL = "https://api.foursquare.com"
    const val GET_PLACES_NEARBY = "$BASE_URL/v3/places/search"

    fun getPlacePhotosUrl(fsqId: String): String {
        return "$BASE_URL/v3/places/$fsqId/photos"
    }

    fun getPlaceDetailsUrl(fsqId: String): String {
        return "$BASE_URL/v3/places/$fsqId"
    }

}