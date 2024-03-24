package dev.maxsiomin.prodhse.core

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openLink(uri: Uri) {
    val intent = Intent(Intent.ACTION_VIEW, uri)
    startActivity(intent)
}
