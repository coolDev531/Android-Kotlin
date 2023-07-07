package com.machado001.hangman.util

import android.content.res.Resources
import java.util.Locale

import android.content.Context
import android.os.Build
import androidx.annotation.IntDef
import java.util.*

object LanguageUtil {

    fun setLanguage(context: Context, @Language language: Int) {
        val resources = context.resources
        val configuration = resources.configuration
        val displayMetrics = resources.displayMetrics
        configuration.setLocale(getLocale(language))
        @Suppress("DEPRECATION")
        resources.updateConfiguration(
            configuration,
            displayMetrics
        )
    }

    private fun getLocale(@Language language: Int): Locale {
        return when (language) {
            Language.SYSTEM -> {
                return Resources.getSystem().configuration.locales[0]
            }

            Language.ENGLISH -> Locale.ENGLISH
            Language.JAPANESE -> Locale.JAPANESE
            Language.SIMPLIFIED_CHINESE -> Locale.SIMPLIFIED_CHINESE
            Language.TRADITIONAL_CHINESE -> Locale.TRADITIONAL_CHINESE
            else -> Locale.SIMPLIFIED_CHINESE
        }
    }

    @IntDef(
        Language.SYSTEM,
        Language.ENGLISH,
        Language.TRADITIONAL_CHINESE,
        Language.SIMPLIFIED_CHINESE,
        Language.JAPANESE
    )
    @Target(AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Language {

        companion object {
            const val SYSTEM = -1
            const val ENGLISH = 1 // 英语
            const val SIMPLIFIED_CHINESE = 0 // 简体中文
            const val TRADITIONAL_CHINESE = 2 // 繁体中文
            const val JAPANESE = 3//日语
        }
    }
}