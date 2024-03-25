package dev.maxsiomin.prodhse.feature.auth.data.remote.nager

object HttpRoutes {

    private const val BASE_URL = "https://date.nager.at"

    fun getHolidaysUrl(year: String, countryCode: String): String {
        return "$BASE_URL/api/v3/PublicHolidays/$year/$countryCode"
    }

}