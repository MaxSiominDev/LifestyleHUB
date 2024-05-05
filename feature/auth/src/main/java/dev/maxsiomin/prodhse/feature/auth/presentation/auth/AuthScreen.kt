package dev.maxsiomin.prodhse.feature.auth.presentation.auth

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.maxsiomin.common.util.CollectFlow
import dev.maxsiomin.prodhse.core.presentation.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.theme.CyanThemeColor
import dev.maxsiomin.prodhse.feature.auth.theme.CyanThemeColorGradientEnd
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
internal fun AuthScreenRoot(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
) {

    BackHandler {
        navController.popBackStack(Screen.ProfileScreen.route, inclusive = true)
    }

    CollectFlow(viewModel.effectFlow) { effect ->
        when (effect) {
            is AuthViewModel.Effect.NavigateToLoginScreen -> {
                navController.navigate(Screen.LoginScreen.route)
            }

            is AuthViewModel.Effect.NavigateToSignupScreen -> {
                navController.navigate(Screen.SignupScreen.route)
            }
        }
    }

    AuthScreen(onEvent = viewModel::onEvent)

}

@Composable
private fun AuthScreen(onEvent: (AuthViewModel.Event) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    1f to CyanThemeColor, 1f to CyanThemeColorGradientEnd,
                ),
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(horizontal = 32.dp),
            text = "Welcome to LifestyleHUB!",
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            fontSize = 25.sp,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(horizontal = 32.dp),
            text = "Dive into a world where your lifestyle takes center stage",
            fontWeight = FontWeight.Normal,
            color = Color.White,
            fontSize = 16.sp,
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 2.dp),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                onEvent(AuthViewModel.Event.LoginClicked)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = CyanThemeColor,
            ),
        ) {
            Text(text = "Log in")
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 2.dp),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                onEvent(AuthViewModel.Event.SignupClicked)
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White,
            ),
            border = BorderStroke(1.dp, Color.White)
        ) {
            Text(text = "Sign up")
        }

        Spacer(modifier = Modifier.height(32.dp))
    }

    Image(
        painter = painterResource(id = R.drawable.sunbed),
        contentDescription = stringResource(R.string.sunbed),
        modifier = Modifier
            .size(110.dp)
            .padding(start = 40.dp, top = 40.dp)
    )
}

@Preview
@Composable
private fun AuthScreenPreview() {
    ProdhseTheme {
        AuthScreen(onEvent = {})
    }
}