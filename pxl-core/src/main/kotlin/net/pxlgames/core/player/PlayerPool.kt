package net.pxlgames.core.player

import eu.thesimplecloud.api.CloudAPI
import net.pxlgames.core.event.PlayerUpdateEvent
import net.pxlgames.core.player.name.PlayerNamePool
import net.pxlgames.core.redis.adapter.RedisHashAdapter
import java.util.*

object PlayerPool : IPlayerPool {

    private val adapter = RedisHashAdapter(PxlPlayer::class.java, "players")

    /*
    Get PxlPlayer by UUID

    @param uuid - UUID of the player

    @return The Player with the given UUID or if not found, create it
     */
    override fun getPlayer(uuid: UUID): IPxlPlayer = adapter.get(uuid.toString()) ?: createPlayer(uuid)

    /*
    Get PxlPlayer by name (case insensitive) using the defaullt player name pool

    @param name - The name of the player

    @return The player with the given name if exists, otherwise null
     */
    override fun getPlayerByName(name: String): IPxlPlayer? {

        val playerUUID = PlayerNamePool.getPlayerByName(name) ?: return null

        return getPlayer(UUID.fromString(playerUUID))

    }

    /*
    Delete the old player data from redis and insert the updated player into redis

    @param player - The player to update
     */
    override fun updatePlayer(player: IPxlPlayer) = adapter.set(player.uuid.toString(), player as PxlPlayer)
        .also {
            try {
                CloudAPI.instance.getEventManager().call(PlayerUpdateEvent(player))
            } catch (e: Exception) {
                println("Disabled event call for Testing")
            }
        }

    /*
    Check if player name exists and or if it is already in use
    Called when a player joins the server.

    @param uuid The UUID of the player.
    @param name The name of the player.

     */
    override fun checkPlayer(uuid: UUID, name: String) {

        val player = getPlayer(uuid)

        if (player !is PxlPlayer) return

        if (player.name == "null") {
            player.name = name
            player.update()

            // if a player in the db has the same name as the one we are checking, remove the order player with the same name from the db
            //create the new one in PlayerNamePool
            PlayerNamePool.updatePlayerName(uuid, name)
        } else if (player.name != name) {
            player.name = name
            player.update()

            // remove Player with old name from PlayerNamePool
            // add Player to PlayerNamePool
            PlayerNamePool.updatePlayerName(uuid, name)
        }

    }

    /*
    Create a new player

    @param uuid - the UUID of the player

    @return the new player without the name set
     */
    override fun createPlayer(uuid: UUID): IPxlPlayer {
        val player = PxlPlayer(uuid, "null", "en", 0, -1, mutableMapOf())

        player.update()
        return player
    }

}