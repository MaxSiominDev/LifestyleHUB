package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.browse_photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import dev.maxsiomin.prodhse.core.presentation.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.home.R

@Composable
internal fun BrowsePhotoScreenRoot(viewModel: BrowsePhotoViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    BrowsePhotoScreen(state = state)

}

@Composable
private fun BrowsePhotoScreen(state: BrowsePhotoViewModel.State) {
    val isPreview = LocalInspectionMode.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        val imageModifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
        if (isPreview) {
            Image(
                modifier = imageModifier,
                painter = painterResource(id = R.drawable.place_preview),
                contentDescription = null
            )
        } else {
            AsyncImage(
                modifier = imageModifier,
                model = state.url,
                contentDescription = stringResource(id = R.string.fullscreen_image)
            )
        }
    }
}

@Preview
@Composable
private fun BrowsePhotoScreenPreview() {
    ProdhseTheme {
        BrowsePhotoScreen(state = BrowsePhotoViewModel.State(url = ""))
    }
}