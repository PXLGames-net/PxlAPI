package net.pxlgames.core.lang

interface ILanguage {

    /*
    Language code from https://developers.google.com/admin-sdk/directory/v1/languages
     */
    val languageCode: String

    /*
    get a message from the language pool
     */
    fun getMessage(key: String): String

}