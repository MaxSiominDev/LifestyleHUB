package dev.maxsiomin.prodhse.feature.venues.presentation.home

import dev.maxsiomin.prodhse.feature.venues.domain.PlaceModel

sealed class FeedItem {
    data object Weather : FeedItem()
    data class Venue(val placeModel: PlaceModel) : FeedItem()
}