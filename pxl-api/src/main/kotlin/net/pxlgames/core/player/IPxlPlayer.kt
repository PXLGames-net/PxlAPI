package net.pxlgames.core.player

import java.util.*

interface IPxlPlayer {

    val uuid: UUID
    var name: String

    /*
    returns the player language as a language code(https://developers.google.com/admin-sdk/directory/v1/languages)
     */
    var lang: String

    var coins: Long

    /*
    returns the date til the player is banned from the server as a Unix timestamp

    if the player is not banned, return -1
     */
    var bannedTil: Long

    fun onlineTime(): Long
    val firstLogin: Long
    val lastLogin: Long

    /*
    returns a map of properties from the player

    the data will be serialized to JSON
    it's recommended to use Data class to store the data
     */
    val properties: MutableMap<String, Any>

}