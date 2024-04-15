package dev.maxsiomin.prodhse.core.util

import java.util.Locale
import javax.inject.Inject

interface LocaleManager {
    fun getLocaleLanguage(): String

    companion object {
        const val DEFAULE_LOCALE = "en"
    }
}

class LocaleManagerImpl @Inject constructor() : LocaleManager {

    private val currentLocale = Locale.getDefault()

    private val localeLanguage = currentLocale.language

    private val supportedLocales = listOf(LocaleManager.DEFAULE_LOCALE)

    override fun getLocaleLanguage(): String {
        return if (localeLanguage in supportedLocales) localeLanguage else "en"
    }

}
