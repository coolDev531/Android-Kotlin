package com.machado001.hangman.util

import java.text.Normalizer

object StringUtil {

    fun removeWhitespacesAndHyphens(word: String): String {
        return word.filterNot { it.isWhitespace() || it == '-' }
    }

    fun normalizeWord(word: String): String {
//      regex to select all characters that are not in the ASCII set.
        val regex = "[^\\p{ASCII}]".toRegex()
        return Normalizer.normalize(word, Normalizer.Form.NFD).replace(regex, "")
    }
}