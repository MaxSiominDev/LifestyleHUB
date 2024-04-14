package dev.maxsiomin.common.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openPhoneNumber(phoneNumber: String) {
    Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }.also(::startActivity)
}
