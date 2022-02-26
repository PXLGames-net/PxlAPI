package net.pxlgames.core.lang

import net.pxlgames.core.redis.RedisHandler

object LanguagePool : ILanguagePool {

    private val adapter = RedisHandler.asyncCommands
    val PREFIX = getMessage("en", "pxl.prefix")

    /*
     * Returns the language by languageCode (https://developers.google.com/admin-sdk/directory/v1/languages)
     *
     * @param languageCode The language code
     *
     * @returns The DefaultLanguage
     */
    override fun getLanguage(languageCode: String) = Language(languageCode)

    /*
     * @returns the message by Language and key
     *
     * @param language - The language (DefaultLanguage)
     * @param key - The key from the redis hash
     */
    override fun getMessage(language: ILanguage, key: String) =
        adapter.hget("language:${language.languageCode}", key).get() ?: key

    /*
     * @returns the message by language code and key
     *
     * @param languageCode - The language code (https://developers.google.com/admin-sdk/directory/v1/languages)
     * @param key - The key from the redis hash
     */
    override fun getMessage(languageCode: String, key: String) =
        adapter.hget("language:$languageCode", key).get() ?: key
}