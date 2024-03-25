package dev.maxsiomin.prodhse.core.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openLink(uri: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(intent)
}
