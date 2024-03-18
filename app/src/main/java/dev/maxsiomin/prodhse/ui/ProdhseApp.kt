package dev.maxsiomin.prodhse.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.maxsiomin.authlib.AuthManager

@Composable
fun ProdhseApp() {

   val authManager = remember {
      AuthManager.instance
   }
   ProdhseNavHost(authManager = authManager)

}


