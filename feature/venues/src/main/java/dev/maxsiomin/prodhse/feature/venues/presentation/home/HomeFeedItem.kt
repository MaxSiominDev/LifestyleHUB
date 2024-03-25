package dev.maxsiomin.prodhse.feature.venues.presentation.home

import dev.maxsiomin.prodhse.feature.venues.domain.PlaceModel

sealed class HomeFeedItem {
    data object Weather : HomeFeedItem()
    data class Venue(val placeModel: PlaceModel) : HomeFeedItem()
}