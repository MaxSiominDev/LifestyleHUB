package dev.maxsiomin.prodhse.feature.auth.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.theme.cyanThemeColor

@Composable
internal fun UsernameTextField(value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {

    TextField(
        modifier = modifier,
        value = value,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = cyanThemeColor,
            focusedIndicatorColor = cyanThemeColor,
            focusedTrailingIconColor = cyanThemeColor,
            focusedLeadingIconColor = cyanThemeColor,
            focusedLabelColor = cyanThemeColor,
        ),
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(text = stringResource(R.string.username)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = stringResource(R.string.lock_icon),
            )
        }
    )

}