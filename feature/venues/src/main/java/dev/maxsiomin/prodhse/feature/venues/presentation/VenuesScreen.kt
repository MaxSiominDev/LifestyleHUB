package dev.maxsiomin.prodhse.feature.venues.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.maxsiomin.prodhse.core.CollectFlow
import dev.maxsiomin.prodhse.core.SnackbarCallback
import dev.maxsiomin.prodhse.core.SnackbarInfo
import dev.maxsiomin.prodhse.core.UiText
import dev.maxsiomin.prodhse.feature.weather.presentation.weatherUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun VenuesScreen(navController: NavController, showSnackbar: SnackbarCallback) {

    val viewModel: VenuesViewModel = hiltViewModel()

    val pullToRefreshState = rememberPullToRefreshState()

    CollectFlow(viewModel.eventFlow) { event ->
        when (event) {
            is VenuesViewModel.UiEvent.FetchingError -> {
                showSnackbar(SnackbarInfo(UiText.DynamicString(event.message)))
            }
        }
    }

    val items: List<FeedItem> = buildList {
        add(FeedItem.Weather)
        addAll(viewModel.state.places.map { FeedItem.Venue(it) })
    }


    Box(
        Modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items) { feedItem ->
                when (feedItem) {
                    is FeedItem.Weather -> {
                        val updateCallback = weatherUi(showSnackbar) {
                            pullToRefreshState.endRefresh()
                        }
                        if (pullToRefreshState.isRefreshing) {
                            updateCallback.invoke()
                            viewModel.onEvent(VenuesViewModel.Event.Refresh)
                        }
                        HorizontalDivider()
                    }

                    is FeedItem.Venue -> {
                        VenueCard(placeModel = feedItem.placeModel)
                    }
                }
            }
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState,
        )
    }

}
