package net.pxlgames.core.lang

class Language(override val languageCode: String) : ILanguage {

    /*
    get a message from the language pool
     */
    override fun getMessage(key: String) = LanguagePool.getMessage(this, key)
}