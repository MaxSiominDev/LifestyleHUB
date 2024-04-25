package dev.maxsiomin.common.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

fun Context.openEmail(email: String) {
    Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
    }.also(::startActivity)
}

fun Context.openLink(uri: String) {
    Intent(Intent.ACTION_VIEW, Uri.parse(uri)).also(::startActivity)
}

fun Context.openPhoneNumber(phoneNumber: String) {
    Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }.also(::startActivity)
}

