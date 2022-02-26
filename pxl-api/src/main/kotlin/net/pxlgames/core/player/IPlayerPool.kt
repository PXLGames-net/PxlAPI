package net.pxlgames.core.player

import java.util.*

interface IPlayerPool {

    /*
    Get PxlPlayer by UUID

    @param uuid - UUID of the player

    @return The Player with the given UUID or if not found, create it
     */
    fun getPlayer(uuid: UUID): IPxlPlayer

    /*
    Get PxlPlayer by name (case insensitive) using the defaullt player name pool

    @param name - The name of the player

    @return The player with the given name if exists, otherwise null
     */
    fun getPlayerByName(name: String): IPxlPlayer?

    /*
    Delete the old player data from redis and insert the updated player into redis

    @param player - The player to update
     */
    fun updatePlayer(player: IPxlPlayer)

    /*
    Check if player name exists and or if it is already in use
    Called when a player joins the server.

    @param uuid The UUID of the player.
    @param name The name of the player.

     */
    fun checkPlayer(uuid: UUID, name: String)

    /*
    Create a new player

    @param uuid - the UUID of the player

    @return the new player without the name set
     */
    fun createPlayer(uuid: UUID): IPxlPlayer
}