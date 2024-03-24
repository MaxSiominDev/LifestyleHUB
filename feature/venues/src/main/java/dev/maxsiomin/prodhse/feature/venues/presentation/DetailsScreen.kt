package dev.maxsiomin.prodhse.feature.venues.presentation

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dev.maxsiomin.prodhse.core.CollectFlow
import dev.maxsiomin.prodhse.core.linkString
import dev.maxsiomin.prodhse.core.openLink
import dev.maxsiomin.prodhse.core.ui.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.venues.R
import dev.maxsiomin.prodhse.feature.venues.domain.PhotoModel
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceDetailsModel
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
internal fun DetailsScreen(
    state: DetailsViewModel.State,
    eventsFlow: Flow<DetailsViewModel.UiEvent>,
    onEvent: (DetailsViewModel.Event) -> Unit,
    navController: NavController,
    fsqId: String,
) {

    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current

    LaunchedEffect(fsqId) {
        onEvent(DetailsViewModel.Event.PassPlaceId(fsqId))
    }

    CollectFlow(eventsFlow) { event ->
        when (event) {
            is DetailsViewModel.UiEvent.NavigateToPhotoScreen -> {
                navController.navigate(
                    Screen.PhotoScreen.withArgs(event.url)
                )
            }
        }
    }

    val placeDetails = state.placeDetails ?: return

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (placeDetails.photos.isNotEmpty()) {
            val mainPhotoModifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
            if (isPreview) {
                Image(
                    modifier = mainPhotoModifier,
                    painter = painterResource(id = R.drawable.place_preview),
                    contentDescription = null
                )
            } else {
                val mainPhotoUrl = placeDetails.photos.first().url
                AsyncImage(
                    modifier = mainPhotoModifier.clickable {
                        onEvent(DetailsViewModel.Event.ImageClicked(url = mainPhotoUrl))
                    },
                    model = mainPhotoUrl,
                    contentDescription = null,
                )
            }

            if (placeDetails.photos.size > 1) {
                LazyRow(modifier = Modifier.height(120.dp)) {
                    items(
                        placeDetails.photos.toMutableList()
                            .apply { removeFirst() }) { currentPhoto ->
                        val photoModifier = Modifier
                            .fillMaxHeight()
                            .padding(4.dp)
                        if (isPreview) {
                            Image(
                                modifier = photoModifier,
                                painter = painterResource(id = R.drawable.place_preview),
                                contentDescription = null
                            )
                        } else {
                            AsyncImage(
                                modifier = photoModifier.clickable {
                                    onEvent(DetailsViewModel.Event.ImageClicked(url = currentPhoto.url))
                                },
                                model = currentPhoto.url,
                                contentDescription = null
                            )
                        }
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier,
                text = placeDetails.name,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                tint = Color(0xFF1BA35C),
                painter = painterResource(id = R.drawable.verified),
                contentDescription = "Verified"
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier,
            text = placeDetails.address,
            fontSize = 16.sp
        )

        placeDetails.rating?.let { rating ->
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(id = R.string.rating, rating),
                fontSize = 16.sp
            )
        }

        placeDetails.website?.let { link ->
            ClickableText(
                modifier = Modifier.padding(vertical = 8.dp),
                text = linkString(link),
                onClick = {
                    context.openLink(Uri.parse(link))
                }
            )
        }


        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = placeDetails.name,
            fontSize = 20.sp
        )


    }

}

@Preview
@Composable
private fun DetailsScreenPreview() {

    ProdhseTheme {
        DetailsScreen(
            state = DetailsViewModel.State(
                placeDetails = PlaceDetailsModel(
                    name = "Кафе Studio 89.5",
                    address = "Маросейка, д. 13, 101000, Москва",
                    photos = listOf(
                        PhotoModel(id = "", url = ""),
                        PhotoModel(id = "", url = ""),
                        PhotoModel(id = "", url = ""),
                        PhotoModel(id = "", url = ""),
                        PhotoModel(id = "", url = ""),
                        PhotoModel(id = "", url = ""),
                    ),
                    workingHours = listOf(),
                    isVerified = true,
                    rating = 9.3,
                    website = "https://megapolism.ru",
                    isOpenNow = true,
                    id = "",
                    categories = "Cafe",
                    timeUpdated = System.currentTimeMillis(),
                )
            ),
            onEvent = {},
            fsqId = "",
            eventsFlow = flow { },
            navController = rememberNavController(),
        )
    }

}