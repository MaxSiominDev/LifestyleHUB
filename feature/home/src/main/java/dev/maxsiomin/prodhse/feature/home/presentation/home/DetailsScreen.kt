package dev.maxsiomin.prodhse.feature.home.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dev.maxsiomin.prodhse.core.util.CollectFlow
import dev.maxsiomin.prodhse.core.extensions.linkString
import dev.maxsiomin.prodhse.core.extensions.openEmail
import dev.maxsiomin.prodhse.core.extensions.openLink
import dev.maxsiomin.prodhse.core.extensions.openPhoneNumber
import dev.maxsiomin.prodhse.core.ui.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.home.domain.PhotoModel
import dev.maxsiomin.prodhse.feature.home.domain.PlaceDetailsModel
import dev.maxsiomin.prodhse.feature.home.R
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

            is DetailsViewModel.UiEvent.NavigateToAddPlanScreen -> {
                navController.navigate(
                    Screen.AddPlanScreen.withArgs(event.fsqId)
                )
            }
        }
    }

    val placeDetails = state.placeDetails ?: return

    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {

        Spacer(modifier = Modifier.height(10.dp))

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
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = placeDetails.categories,
        )
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
                    context.openLink(link)
                }
            )
        }

        placeDetails.phone?.let { phone ->
            ClickableText(
                modifier = Modifier.padding(vertical = 8.dp),
                text = linkString(phone),
                onClick = {
                    context.openPhoneNumber(phone)
                }
            )
        }

        placeDetails.email?.let { email ->
            ClickableText(
                modifier = Modifier.padding(vertical = 8.dp),
                text = linkString(email),
                onClick = {
                    context.openEmail(email)
                }
            )
        }

        if (state.placeDetails.isOpenNow) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(id = R.string.open_now),
                fontSize = 16.sp,
                color = Color(0xFF1BA35C),
            )
        } else {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(id = R.string.closed_now),
                fontSize = 16.sp,
                color = Color.Red,
            )
        }

        placeDetails.workingHours?.let { schedule ->
            Column {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = stringResource(id = R.string.working_hours),
                    fontSize = 17.sp
                )
                schedule.forEach {
                    Text(
                        text = it,
                        fontSize = 15.sp
                    )
                }
            }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(
                onClick = {
                    onEvent(DetailsViewModel.Event.IconAddToPlansClicked(fsqId))
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.AddCircleOutline,
                    contentDescription = "Add to plans"
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        contentAlignment = Alignment.BottomEnd,
    ) {
        FloatingActionButton(
            onClick = { onEvent(DetailsViewModel.Event.IconAddToPlansClicked(fsqId)) }
        ) {
            Icon(
                imageVector = Icons.Outlined.AddCircleOutline,
                contentDescription = stringResource(R.string.add_to_plans)
            )
        }
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
                    workingHours = listOf(
                        "Mon-Thu 11:00-23:00",
                        "Fri 11:00-24:00",
                        "Sat-Sun 0:00-2:00",
                    ),
                    isVerified = true,
                    rating = 9.3,
                    website = "https://megapolism.ru",
                    isOpenNow = true,
                    fsqId = "",
                    categories = "Cafe",
                    timeUpdated = System.currentTimeMillis(),
                    email = "example@example.com",
                    phone = "+79291234576",
                )
            ),
            onEvent = {},
            fsqId = "",
            eventsFlow = flow { },
            navController = rememberNavController(),
        )
    }

}
