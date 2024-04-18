package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.authlib.domain.UserInfo
import dev.maxsiomin.prodhse.core.presentation.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.presentation.components.NearestHolidayDialog
import dev.maxsiomin.prodhse.feature.auth.presentation.components.RandomActivityDialog
import dev.maxsiomin.prodhse.feature.auth.theme.cyanThemeColor
import dev.maxsiomin.prodhse.feature.auth.theme.cyanThemeColorGradientEnd
import dev.maxsiomin.prodhse.navdestinations.Screen

@Composable
internal fun ProfileScreen(
    state: ProfileViewModel.State,
    onEvent: (ProfileViewModel.Event) -> Unit,
    navController: NavController
) {

    val isPreview = LocalInspectionMode.current

    if (state.authStatus == AuthStatus.NotAuthenticated) {
        navController.navigate(Screen.AuthScreen.route)
        return
    }

    when (state.authStatus) {
        AuthStatus.NotAuthenticated -> {

        }

        AuthStatus.Loading -> return
        is AuthStatus.Authenticated -> Unit
    }

    if (state.showRandomActivityDialog) {
        RandomActivityDialog(
            activity = state.randomActivity!!,
            onDismissRequest = {
                onEvent(ProfileViewModel.Event.DismissRandomActivityDialog)
            }
        )
    }

    if (state.showNearestHolidayDialog) {
        NearestHolidayDialog(
            holiday = state.nearestHoliday!!,
            onDismissRequest = {
                onEvent(ProfileViewModel.Event.DismissHolidayDialog)
            }
        )
    }

    val userInfo = (state.authStatus as? AuthStatus.Authenticated)?.userInfo ?: return

    Column(
        Modifier
            .background(
                brush = Brush.linearGradient(
                    1f to cyanThemeColor, 1f to cyanThemeColorGradientEnd,
                )
            )
            .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        IconButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp),
            onClick = {
                onEvent(ProfileViewModel.Event.LogoutClicked)
            },
        ) {
            Icon(
                tint = Color.White,
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = stringResource(id = R.string.log_out),
            )
        }

        Spacer(modifier = Modifier.weight(0.25f))

        val imageModifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
        if (isPreview) {
            Image(
                modifier = imageModifier,
                painter = painterResource(id = R.drawable.preview_avatar),
                contentDescription = null,
            )
        } else {
            AsyncImage(
                modifier = imageModifier,
                model = userInfo.avatarUrl,
                contentDescription = stringResource(id = R.string.avatar)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "@${userInfo.username}", color = Color.White, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = userInfo.fullName, color = Color.White, fontSize = 25.sp)

        Spacer(modifier = Modifier.weight(0.75f))

        Column(
            Modifier
                .width(IntrinsicSize.Max)
                .padding(16.dp))
        {

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = state.randomActivity != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = cyanThemeColor,
                ),
                onClick = {
                    onEvent(ProfileViewModel.Event.BoredClicked)
                }
            ) {
                Text(text = stringResource(R.string.i_m_bored))
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = state.nearestHoliday != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = cyanThemeColor,
                ),
                onClick = {
                    onEvent(ProfileViewModel.Event.HolidayClicked)
                }
            ) {
                Text(text = stringResource(R.string.the_nearest_holiday))
            }

        }
    }

}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProdhseTheme {
        ProfileScreen(
            state = ProfileViewModel.State(
                authStatus = AuthStatus.Authenticated(
                    userInfo = UserInfo(
                        username = "maxsiomindev",
                        fullName = "Max S.",
                        avatarUrl = "",
                        passwordHash = "",
                    )
                )
            ),
            onEvent = {},
            navController = rememberNavController()
        )
    }
}