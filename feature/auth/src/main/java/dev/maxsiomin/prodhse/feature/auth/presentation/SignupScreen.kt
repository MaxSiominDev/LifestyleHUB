package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.common.util.CollectFlow
import dev.maxsiomin.prodhse.core.presentation.FireworksAnimation
import dev.maxsiomin.prodhse.core.presentation.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.presentation.components.LineOrLine
import dev.maxsiomin.prodhse.feature.auth.presentation.components.PasswordTextField
import dev.maxsiomin.prodhse.feature.auth.presentation.components.UsernameTextField
import dev.maxsiomin.prodhse.feature.auth.theme.cyanThemeColor
import dev.maxsiomin.prodhse.feature.auth.theme.cyanThemeColorGradientEnd
import dev.maxsiomin.prodhse.feature.auth.theme.grayThemeColor
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun SignupScreen(
    state: SignupViewModel.State,
    eventsFlow: Flow<SignupViewModel.UiEvent>,
    onEvent: (SignupViewModel.Event) -> Unit,
    showSnackbar: SnackbarCallback,
    navController: NavController
) {

    val context = LocalContext.current

    CollectFlow(flow = eventsFlow) { event ->
        when (event) {

            is SignupViewModel.UiEvent.NavigateToLoginScreen -> {
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(Screen.AuthScreen.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }

            is SignupViewModel.UiEvent.NavigateToSuccessfulRegistrationScreen -> {
                navController.navigate(Screen.SuccessfulRegistrationScreen.route) {
                    popUpTo(Screen.AuthScreen.route)
                }
            }

            is SignupViewModel.UiEvent.ShowMessage -> showSnackbar(
                dev.maxsiomin.common.presentation.SnackbarInfo(event.message)
            )

        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    1f to cyanThemeColor, 1f to cyanThemeColorGradientEnd,
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(modifier = Modifier.weight(0.15f))
            Text(
                modifier = Modifier.padding(start = 32.dp),
                text = stringResource(R.string.create),
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp
            )
            Text(
                modifier = Modifier.padding(start = 32.dp),
                text = stringResource(R.string.account),
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.weight(0.85f))
        }

        Card(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = dev.maxsiomin.common.presentation.components.TopRoundedCornerShape(20.dp),
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                Spacer(modifier = Modifier.height(50.dp))

                UsernameTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    value = state.usernameState.text,
                    onValueChange = {
                        onEvent(SignupViewModel.Event.UsernameChanged(it))
                    },
                    error = state.usernameState.error?.asString()
                )

                Spacer(modifier = Modifier.height(8.dp))

                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    value = state.password,
                    onValueChange = {
                        onEvent(SignupViewModel.Event.PasswordChanged(it))
                    },
                    error = state.passwordError?.asString()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 2.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        onEvent(SignupViewModel.Event.SignupClicked)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = cyanThemeColor,
                        contentColor = Color.White,
                    ),
                ) {
                    Text(text = stringResource(id = R.string.sign_up))
                }

                LineOrLine(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 16.dp)
                )

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 2.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        onEvent(SignupViewModel.Event.LoginClicked)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = grayThemeColor,
                    ),
                    border = BorderStroke(1.dp, grayThemeColor)
                ) {
                    Text(text = stringResource(R.string.log_in))
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

    if (state.showFireworksAnimation) {
        FireworksAnimation()
    }

}

@Preview
@Composable
private fun SignupScreenPreview() {
    ProdhseTheme {
        SignupScreen(SignupViewModel.State(), flow {}, {}, {}, navController = rememberNavController())
    }
}