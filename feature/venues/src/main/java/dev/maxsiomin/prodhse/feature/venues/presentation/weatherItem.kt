package dev.maxsiomin.prodhse.feature.venues.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import dev.maxsiomin.prodhse.core.SnackbarCallback
import dev.maxsiomin.prodhse.core.ui.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.venues.R
import dev.maxsiomin.prodhse.feature.weather.presentation.UpdateCallback
import dev.maxsiomin.prodhse.feature.weather.presentation.weatherUi

/*@Composable
internal fun weatherItem(
    state: VenuesViewModel.State,
    onEvent: (VenuesViewModel.Event) -> Unit,
    showSnackbar: SnackbarCallback
): UpdateCallback? {

    val context = LocalContext.current
    var updateCallback: UpdateCallback? = null

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val annotatedLinkString: AnnotatedString = remember(state.expandWeather) {
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        val text =
                            if (state.expandWeather) context.getString(R.string.hide)
                            else context.getString(R.string.show)
                        append(text)
                    }
                }
            }
            ClickableText(text = annotatedLinkString, onClick = { _ ->
                onEvent(VenuesViewModel.Event.ExpandTextClicked)
            })
        }

        if (state.expandWeather) {
            updateCallback = weatherUi(
                showSnackbar = showSnackbar,
                endRefresh = {}
            )
        }
    }

    return updateCallback
}

@Preview
@Composable
private fun WeatherItemPreview() {
    ProdhseTheme {
        weatherItem(VenuesViewModel.State(), {}, {})
    }
}*/
