package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner

import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails
import dev.maxsiomin.prodhse.feature.home.domain.model.Plan

sealed class PlannerFeedItem {
    data class Place(val place: PlaceDetails, val plan: Plan) : PlannerFeedItem()
}