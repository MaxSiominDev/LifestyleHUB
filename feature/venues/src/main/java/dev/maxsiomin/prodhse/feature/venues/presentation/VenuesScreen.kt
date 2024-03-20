package dev.maxsiomin.prodhse.feature.venues.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.maxsiomin.prodhse.core.SnackbarCallback
import dev.maxsiomin.prodhse.feature.weather.presentation.UpdateCallback
import dev.maxsiomin.prodhse.feature.weather.presentation.weatherUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun VenuesScreen(showSnackbar: SnackbarCallback) {

    val viewModel: VenuesViewModel = hiltViewModel()

    val pullToRefreshState = rememberPullToRefreshState()

    Box(Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                val updateCallback = weatherUi(showSnackbar) {
                    pullToRefreshState.endRefresh()
                }
                if (pullToRefreshState.isRefreshing) {
                    updateCallback.invoke()
                }
            }
            items(10) {
                Column {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Green))
                    HorizontalDivider()
                }
            }
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState,
        )
    }


}
