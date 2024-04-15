package dev.maxsiomin.prodhse.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

interface DateFormatter {
    fun formatDate(epochMillis: Long): String
}

class DefaultDateFormatter @Inject constructor() : DateFormatter {

    override fun formatDate(epochMillis: Long): String {
        val date = Date(epochMillis)
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

}

/**class RussianDateFormatter @Inject constructor() : DateFormatter {

    override fun formatDate(epochMillis: Long): String {
        val date = Date(epochMillis)
        val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

}*/