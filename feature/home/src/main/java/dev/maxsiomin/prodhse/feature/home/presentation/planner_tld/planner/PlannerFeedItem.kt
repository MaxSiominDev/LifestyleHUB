package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner

import dev.maxsiomin.prodhse.feature.home.domain.PlaceDetailsModel
import dev.maxsiomin.prodhse.feature.home.domain.PlanModel

sealed class PlannerFeedItem {
    data class Venue(val place: PlaceDetailsModel, val plan: PlanModel) : PlannerFeedItem()
}