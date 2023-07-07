package com.machado001.hangman.util

import java.text.Normalizer

object StringUtil {
    fun isWordCorrectlyGuessed(wordChosen: String?, correctLetters: Set<Char>): Boolean {
        return wordChosen?.run {
            val wordWithoutWhitespaces = removeWhitespacesAndHyphens(this)
            val normalizedWord = normalizeWord(wordWithoutWhitespaces)
            correctLetters.containsAll(normalizedWord.toList())
        } == true
    }

    private fun removeWhitespacesAndHyphens(word: String): String {
        return word.filterNot { it.isWhitespace() || it == '-' }
    }

    private fun normalizeWord(word: String): String {
        val regex = "[^\\p{ASCII}]".toRegex()
        return Normalizer.normalize(word, Normalizer.Form.NFD).replace(regex, "")
    }
}