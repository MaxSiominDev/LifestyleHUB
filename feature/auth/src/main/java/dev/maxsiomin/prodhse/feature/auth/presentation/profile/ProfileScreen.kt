package dev.maxsiomin.prodhse.feature.auth.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.CircularProgressIndicator
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
import dev.maxsiomin.authlib.domain.model.UserInfo
import dev.maxsiomin.prodhse.core.presentation.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.theme.CyanThemeColor
import dev.maxsiomin.prodhse.feature.auth.theme.CyanThemeColorGradientEnd
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

    if (state.authStatus is AuthStatus.Loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )
        }
    }

    val userInfo = (state.authStatus as? AuthStatus.Authenticated)?.userInfo ?: return

    Column(
        Modifier
            .background(
                brush = Brush.linearGradient(
                    1f to CyanThemeColor, 1f to CyanThemeColorGradientEnd,
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