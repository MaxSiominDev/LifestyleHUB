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
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.prodhse.core.ui.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.presentation.components.ForgotPasswordDialog
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
fun LoginScreen(
    state: LoginViewModel.State,
    eventsFlow: Flow<LoginViewModel.UiEvent>,
    onEvent: (LoginViewModel.Event) -> Unit,
    showSnackbar: SnackbarCallback,
    navController: NavController
) {

    dev.maxsiomin.common.util.CollectFlow(flow = eventsFlow) { event ->
        when (event) {

            is LoginViewModel.UiEvent.NavigateToSignupScreen -> {
                navController.navigate(Screen.SignupScreen.route) {
                    popUpTo(Screen.AuthScreen.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }

            is LoginViewModel.UiEvent.NavigateToProfileScreen -> {
                navController.popBackStack(route = Screen.ProfileScreen.route, inclusive = false)
            }

            is LoginViewModel.UiEvent.ShowError -> {
                showSnackbar(
                    dev.maxsiomin.common.presentation.SnackbarInfo(event.message)
                )
            }
        }
    }

    if (state.showForgotPasswordDialog) {
        ForgotPasswordDialog(
            onDismissRequest = { onEvent(LoginViewModel.Event.DismissForgotPasswordDialog) },
        )
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
                text = stringResource(R.string.welcome),
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp
            )
            Text(
                modifier = Modifier.padding(start = 32.dp),
                text = stringResource(R.string.back),
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.weight(0.85f))
        }

        Card(
            Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .align(Alignment.BottomCenter),
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
                    value = state.username,
                    onValueChange = {
                        onEvent(LoginViewModel.Event.UsernameChanged(it))
                    },
                    error = state.usernameError?.asString()
                )

                Spacer(modifier = Modifier.height(8.dp))

                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    value = state.password,
                    onValueChange = {
                        onEvent(LoginViewModel.Event.PasswordChanged(it))
                    },
                    error = state.passwordError?.asString(),
                )

                Spacer(modifier = Modifier.height(8.dp))

                ClickableText(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 32.dp),
                    text = AnnotatedString(stringResource(id = R.string.forgot_password)),
                    style = TextStyle(
                        color = cyanThemeColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                    onClick = {
                        onEvent(LoginViewModel.Event.ForgotPasswordClicked)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 2.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        onEvent(LoginViewModel.Event.LoginClicked)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = cyanThemeColor,
                        contentColor = Color.White,
                    ),
                ) {
                    Text(text = stringResource(id = R.string.log_in))
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
                        onEvent(LoginViewModel.Event.SignupClicked)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = grayThemeColor,
                    ),
                    border = BorderStroke(1.dp, grayThemeColor)
                ) {
                    Text(text = stringResource(R.string.sign_up))
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

}

@Preview
@Composable
private fun LoginScreenPreview() {
    ProdhseTheme {
        LoginScreen(LoginViewModel.State(), flow {}, {}, {}, navController = rememberNavController())
    }
}