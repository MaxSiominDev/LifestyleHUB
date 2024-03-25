package dev.maxsiomin.prodhse.core.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openPhoneNumber(uri: Uri) {
    val intent = Intent(Intent.ACTION_CALL, uri)
    startActivity(intent)
}
