package dev.maxsiomin.prodhse.core.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openEmail(email: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
    }.also(::startActivity)
}