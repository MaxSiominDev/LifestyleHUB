package dev.maxsiomin.prodhse.core

import java.util.Locale
import javax.inject.Inject

interface LocaleManager {
    fun getLocaleLanguage(): String
}

class LocaleManagerImpl @Inject constructor() : LocaleManager {

    private val currentLocale = Locale.getDefault()

    private val localeLanguage = currentLocale.language

    private val defaultLocale = "en"

    private val supportedLocales = listOf(defaultLocale, "ru")

    override fun getLocaleLanguage(): String {
        return if (localeLanguage in supportedLocales) localeLanguage else "en"
    }

}
