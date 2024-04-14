package dev.maxsiomin.common.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openLink(uri: String) {
    Intent(Intent.ACTION_VIEW, Uri.parse(uri)).also(::startActivity)
}
