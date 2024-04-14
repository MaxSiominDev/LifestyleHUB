package dev.maxsiomin.common.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openEmail(email: String) {
    Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
    }.also(::startActivity)
}