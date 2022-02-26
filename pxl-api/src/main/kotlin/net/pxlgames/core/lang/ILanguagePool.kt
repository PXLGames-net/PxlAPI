package net.pxlgames.core.lang

interface ILanguagePool {

    /*
     * Returns the language by languageCode (https://developers.google.com/admin-sdk/directory/v1/languages)
     *
     * @param languageCode The language code
     *
     * @returns The DefaultLanguage
     */
    fun getLanguage(languageCode: String): ILanguage

    /*
     * @returns the message by Language and key
     *
     * @param language - The language (DefaultLanguage)
     * @param key - The key from the redis hash
     */
    fun getMessage(language: ILanguage, key: String): String

    /*
     * @returns the message by language code and key
     *
     * @param languageCode - The language code (https://developers.google.com/admin-sdk/directory/v1/languages)
     * @param key - The key from the redis hash
     */
    fun getMessage(languageCode: String, key: String): String
}