package dev.maxsiomin.prodhse.feature.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.theme.GrayThemeColor

/** Name is terrible but check pthe preview */
@Composable
internal fun LineOrLine(modifier: Modifier = Modifier, color: Color = GrayThemeColor, lineHeight: Dp = 1.dp) {

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

        Spacer(
            modifier = Modifier
                .background(color)
                .weight(0.5f)
                .height(lineHeight)
        )

        Text(text = stringResource(id = R.string.or), modifier = Modifier.padding(horizontal = 8.dp))

        Spacer(
            modifier = Modifier
                .background(color)
                .weight(0.5f)
                .height(lineHeight)
        )

    }

}

@Preview
@Composable
private fun LineOrLinePrev() {
    LineOrLine(modifier = Modifier.fillMaxWidth())
}