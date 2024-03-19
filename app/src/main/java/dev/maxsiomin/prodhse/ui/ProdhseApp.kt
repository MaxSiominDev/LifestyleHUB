package dev.maxsiomin.prodhse.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.prodhse.R
import dev.maxsiomin.prodhse.core.SnackbarInfo
import kotlinx.coroutines.launch

@Composable
fun ProdhseApp() {

    val authManager = remember {
        AuthManager.instance
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val showSnackbar = remember {
        { info: SnackbarInfo ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = info.message.asString(context),
                    actionLabel = context.getString(R.string.hide)
                )
            }
            Unit
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            ProdhseNavHost(authManager = authManager, showSnackbar = showSnackbar)
        }
    }
   
}
