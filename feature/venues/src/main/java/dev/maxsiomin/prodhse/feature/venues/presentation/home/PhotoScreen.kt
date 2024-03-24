package dev.maxsiomin.prodhse.feature.venues.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.maxsiomin.prodhse.core.ui.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.venues.R

@Composable
fun PhotoScreen(url: String) {

    val isPreview = LocalInspectionMode.current

    Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
        val modifier = Modifier.fillMaxSize().padding(4.dp)
        if (isPreview) {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.place_preview),
                contentDescription = null
            )
        } else {
            AsyncImage(
                modifier = modifier,
                model = url,
                contentDescription = stringResource(id = R.string.fullscreen_image)
            )
        }
    }

}

@Preview
@Composable
private fun PhotoScreenPreview() {
    ProdhseTheme {
        PhotoScreen(url = "")
    }
}