package dev.maxsiomin.prodhse.feature.auth.presentation.successful_registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
internal fun SuccessfulRegistrationScreenRoot(
    navController: NavController,
    viewModel: SuccessfulRegistrationViewModel = hiltViewModel(),
) {

    CollectFlow(viewModel.effectFlow) { effect ->
        when (effect) {
            SuccessfulRegistrationViewModel.Effect.NavigateToLoginScreen -> {
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(Screen.AuthScreen.route) {
                        inclusive = false
                    }
                }
            }
        }
    }

    SuccessfulRegistrationScreen(onEvent = viewModel::onEvent)

}

@Composable
private fun SuccessfulRegistrationScreen(onEvent: (SuccessfulRegistrationViewModel.Event) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CyanThemeColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.weight(0.25f))

        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.thank_you),
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.for_joining_our_club),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.weight(0.25f))

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.now_you_can_login_with_your_credentials),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = CyanThemeColor,
            ),
            onClick = {
                onEvent(SuccessfulRegistrationViewModel.Event.LoginClicked)
            }
        ) {
            Text(text = stringResource(id = R.string.log_in))
        }

        Spacer(modifier = Modifier.weight(0.5f))

    }
}

@Preview
@Composable
private fun SuccessfulRegistrationScreenPreview() {
    ProdhseTheme {
        SuccessfulRegistrationScreen(onEvent = {})
    }
}