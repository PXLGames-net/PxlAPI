package net.pxlgames.core.lang

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LanguagePoolTest {

    @Test
    fun getLanguage() {
        val languagePool = LanguagePool
        val language = languagePool.getLanguage("en")
        assertEquals("en", language.languageCode)
    }

    @Test
    fun getMessage() {
        assertEquals("§bPxlGames | ", LanguagePool.getMessage("en", "pxl.prefix"))
    }

    @Test
    fun testGetMessage() {
        val languagePool = LanguagePool
        val language = languagePool.getLanguage("en")

        assertEquals("§bPxlGames | ", LanguagePool.getMessage(language, "pxl.prefix"))
    }
}